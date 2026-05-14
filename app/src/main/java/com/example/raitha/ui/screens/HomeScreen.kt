package com.example.raitha.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val tips by viewModel.tips.collectAsState()
    val dailyTip by viewModel.dailyTip.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val availableCrops by viewModel.availableCrops.collectAsState()
    val availableDistricts by viewModel.availableDistricts.collectAsState()
    val availableSeasons by viewModel.availableSeasons.collectAsState()
    val pagerState = rememberPagerState(pageCount = { tips.size.coerceAtLeast(1) })

    LaunchedEffect(uiState.selectedCrop, tips.size) {
        if (tips.isNotEmpty() && pagerState.currentPage >= tips.size) {
            pagerState.scrollToPage(0)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HomeHero(
                dailyTipTitle = dailyTip?.title ?: "Daily tip is loading",
                dailyTipBody = dailyTip?.description ?: "Preparing your offline advisory feed."
            )
        }
        item {
            Text(
                text = "Crop focus",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(end = 8.dp)
            ) {
                items(availableCrops) { crop ->
                    AssistChip(
                        onClick = { viewModel.selectCrop(crop) },
                        label = { Text(crop) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (uiState.selectedCrop == crop) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                            labelColor = if (uiState.selectedCrop == crop) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    )
                }
            }
        }
        item {
            SelectorRow(
                selectedDistrict = uiState.selectedDistrict,
                districts = availableDistricts,
                onDistrictSelected = viewModel::setDistrict,
                selectedSeason = uiState.selectedSeason,
                seasons = availableSeasons,
                onSeasonSelected = viewModel::setSeason
            )
        }
        item {
            Button(
                onClick = viewModel::generateTip,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isGeneratingTip) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                }
                Text(if (uiState.isGeneratingTip) "Generating advisory" else "Generate local advisory")
            }
        }
        if (!uiState.aiTip.isNullOrBlank()) {
            item {
                AdvisoryCard(
                    title = "AI + offline advisory",
                    body = uiState.aiTip.orEmpty(),
                    message = uiState.insightMessage
                )
            }
        }
        item {
            Text(
                text = "Swipe flash cards",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        if (tips.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No tips available for this crop yet.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        } else {
            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(460.dp)
                ) { page ->
                    FlashCardItem(
                        tip = tips[page],
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
            item {
                Text(
                    text = "${pagerState.currentPage + 1} / ${tips.size}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectorRow(
    selectedDistrict: String,
    districts: List<String>,
    onDistrictSelected: (String) -> Unit,
    selectedSeason: String,
    seasons: List<String>,
    onSeasonSelected: (String) -> Unit
) {
    androidx.compose.foundation.layout.Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        DropdownField(
            label = "District",
            value = selectedDistrict,
            options = districts,
            modifier = Modifier.weight(1f),
            onSelected = onDistrictSelected
        )
        DropdownField(
            label = "Season",
            value = selectedSeason,
            options = seasons,
            modifier = Modifier.weight(1f),
            onSelected = onSeasonSelected
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownField(
    label: String,
    value: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = false
                        onSelected(option)
                    }
                )
            }
        }
    }
}

@Composable
private fun HomeHero(
    dailyTipTitle: String,
    dailyTipBody: String
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        androidx.compose.foundation.layout.Column(modifier = Modifier.padding(20.dp)) {
            Surface(
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.tertiary
            ) {
                Text(
                    text = "RAITHA VARTA",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = dailyTipTitle,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = dailyTipBody,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
private fun AdvisoryCard(
    title: String,
    body: String,
    message: String?
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        androidx.compose.foundation.layout.Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            if (!message.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.72f)
                )
            }
        }
    }
}
