package com.example.raitha.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raitha.BuildConfig
import com.example.raitha.data.local.TipEntity
import com.example.raitha.data.repository.TipRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val selectedCrop: String = "All",
    val selectedDistrict: String = "Mandya",
    val selectedSeason: String = "Kharif",
    val aiTip: String? = null,
    val isGeneratingTip: Boolean = false,
    val insightMessage: String? = null
)

@HiltViewModel
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel @Inject constructor(
    private val repository: TipRepository
) : ViewModel() {

    private val controls = MutableStateFlow(HomeUiState())

    private val allTips = repository.getAllTips()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val availableCrops: StateFlow<List<String>> = allTips
        .map { tips -> listOf("All") + tips.map { it.cropType }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), listOf("All"))

    val availableDistricts: StateFlow<List<String>> = allTips
        .map { tips -> tips.map { it.district }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), listOf("Mandya"))

    val availableSeasons: StateFlow<List<String>> = allTips
        .map { tips -> tips.map { it.season }.distinct().sorted() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), listOf("Kharif"))

    val dailyTip: StateFlow<TipEntity?> = repository.getDailyTip()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val tips: StateFlow<List<TipEntity>> = controls
        .map { it.selectedCrop }
        .flatMapLatest { selectedCrop ->
            if (selectedCrop == "All") repository.getAllTips() else repository.getTipsByCrop(selectedCrop)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val uiState: StateFlow<HomeUiState> = combine(
        controls,
        availableCrops,
        availableDistricts,
        availableSeasons
    ) { current, crops, districts, seasons ->
        current.copy(
            selectedCrop = current.selectedCrop.takeIf { it in crops } ?: crops.firstOrNull().orEmpty().ifBlank { "All" },
            selectedDistrict = current.selectedDistrict.takeIf { it in districts } ?: districts.firstOrNull().orEmpty().ifBlank { "Mandya" },
            selectedSeason = current.selectedSeason.takeIf { it in seasons } ?: seasons.firstOrNull().orEmpty().ifBlank { "Kharif" }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState())

    fun selectCrop(crop: String) {
        controls.update { current ->
            val matchingTip = allTips.value.firstOrNull { tip ->
                crop == "All" || tip.cropType == crop
            }
            current.copy(
                selectedCrop = crop,
                selectedDistrict = matchingTip?.district ?: current.selectedDistrict,
                selectedSeason = matchingTip?.season ?: current.selectedSeason,
                aiTip = null,
                insightMessage = null
            )
        }
    }

    fun setDistrict(district: String) {
        controls.update { it.copy(selectedDistrict = district) }
    }

    fun setSeason(season: String) {
        controls.update { it.copy(selectedSeason = season) }
    }

    fun generateTip() {
        val snapshot = uiState.value
        viewModelScope.launch {
            controls.update { it.copy(isGeneratingTip = true, insightMessage = null) }

            val crop = snapshot.selectedCrop.takeUnless { it == "All" }
                ?: dailyTip.value?.cropType
                ?: "Paddy"

            val aiResponse = repository.getGeminiTip(
                crop = crop,
                district = snapshot.selectedDistrict,
                season = snapshot.selectedSeason,
                apiKey = BuildConfig.GEMINI_API_KEY
            )

            val fallbackTip = repository.getOfflineTip(crop)
            val finalTip = aiResponse ?: fallbackTip?.let { "${it.title}. ${it.description}" }

            controls.update {
                it.copy(
                    isGeneratingTip = false,
                    aiTip = finalTip ?: "No advisory available at the moment.",
                    insightMessage = if (aiResponse == null) {
                        "Showing offline advisory because the AI response was unavailable."
                    } else {
                        "Fresh advisory generated for ${snapshot.selectedDistrict} in ${snapshot.selectedSeason}."
                    }
                )
            }
        }
    }
}
