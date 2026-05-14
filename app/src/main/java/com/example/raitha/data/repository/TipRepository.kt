package com.example.raitha.data.repository

import com.example.raitha.data.local.TipDao
import com.example.raitha.data.local.TipEntity
import com.example.raitha.data.remote.Content
import com.example.raitha.data.remote.GeminiApiService
import com.example.raitha.data.remote.GeminiRequest
import com.example.raitha.data.remote.InlineData
import com.example.raitha.data.remote.Part
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.delay
import java.io.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

data class GeminiAnalysisResult(
    val text: String? = null,
    val error: String? = null,
    val isFallback: Boolean = false
)

@Singleton
class TipRepository @Inject constructor(
    private val tipDao: TipDao,
    private val geminiApiService: GeminiApiService
) {
    fun getAllTips(): Flow<List<TipEntity>> = tipDao.getAllTips()

    fun getTipsByCrop(cropType: String): Flow<List<TipEntity>> = tipDao.getTipsByCrop(cropType)

    fun getDailyTip(): Flow<TipEntity?> = tipDao.getDailyTip()

    suspend fun getOfflineTip(crop: String? = null): TipEntity? {
        return if (crop.isNullOrBlank()) {
            tipDao.getFirstTip()
        } else {
            tipDao.getFirstTipByCrop(crop) ?: tipDao.getFirstTip()
        }
    }

    suspend fun getGeminiTip(crop: String, district: String, season: String, apiKey: String): String? {
        if (apiKey.isBlank() || apiKey == "null") return null
        return try {
            val prompt = buildString {
                append("You are an agricultural advisor for Karnataka farmers. ")
                append("Provide exactly two short actionable sentences for $crop in $district during $season season. ")
                append("Sentence one must be in English and sentence two must be in Kannada. ")
                append("Keep the advice practical, safe, and field-ready.")
            }
            val request = GeminiRequest(listOf(Content(listOf(Part(prompt)))))
            val response = geminiApiService.generateContent(apiKey, request)
            response.candidates
                .firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?.trim()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun analyzeDisease(imageBase64: String, apiKey: String): GeminiAnalysisResult {
        if (apiKey.isBlank() || apiKey == "null") {
            return GeminiAnalysisResult(
                text = fallbackDiseaseAdvice(),
                error = "Gemini API key is missing.",
                isFallback = true
            )
        }
        val prompt = buildString {
            append("You are an expert crop pathologist. Inspect the crop image and identify the most likely disease, pest, or nutrient deficiency from the image itself. ")
            append("Do not return a generic answer that could fit any crop. ")
            append("If the image is unclear, say that clearly and ask for a sharper close-up. ")
            append("Return exactly these lines: Disease: ..., Symptoms: ..., Treatment: ..., Fertilizer/Nutrient support: .... ")
            append("Keep each line short, specific, and practical for a farmer. ")
            append("If the problem is fungal or bacterial, name the likely spray or drench separately from any fertilizer suggestion. ")
            append("IMPORTANT: Provide the entire response in both English and Kannada languages.")
        }
        val request = GeminiRequest(listOf(Content(listOf(
            Part(text = prompt),
            Part(inline_data = InlineData("image/jpeg", imageBase64))
        ))))

        var lastError: String? = null

        repeat(3) { attempt ->
            try {
                val response = geminiApiService.generateContent(apiKey, request)
                val text = response.candidates
                    .firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?.trim()

                if (!text.isNullOrBlank()) {
                    return GeminiAnalysisResult(text = text)
                }

                lastError = "Gemini returned an empty response."
            } catch (e: HttpException) {
                lastError = "Gemini API HTTP ${e.code()}: ${e.message()}"
                if (!isTransientGeminiError(e.code())) {
                    return GeminiAnalysisResult(error = lastError)
                }
            } catch (e: IOException) {
                lastError = e.message?.takeIf { it.isNotBlank() } ?: e::class.java.simpleName
            } catch (e: Exception) {
                lastError = e.message?.takeIf { it.isNotBlank() } ?: e::class.java.simpleName
                return GeminiAnalysisResult(error = lastError)
            }

            if (attempt < 2) {
                delay((attempt + 1) * 1200L)
            }
        }

        return GeminiAnalysisResult(
            text = fallbackDiseaseAdvice(),
            error = lastError,
            isFallback = true
        )
    }

    private fun isTransientGeminiError(code: Int): Boolean {
        return code == 429 || code == 500 || code == 502 || code == 503 || code == 504
    }

    private fun fallbackDiseaseAdvice(): String {
        return """
            Disease: Unable to confirm from Gemini right now.
            (ರೋಗ: ಪ್ರಸ್ತುತ ಜೆಮಿನಿ ಮೂಲಕ ದೃಢೀಕರಿಸಲು ಸಾಧ್ಯವಿಲ್ಲ.)
            
            Symptoms: Retake a close, well-lit photo that shows the affected leaf or stem clearly.
            (ಲಕ್ಷಣಗಳು: ಬಾಧಿತ ಎಲೆ ಅಥವಾ ಕಾಂಡವನ್ನು ಸ್ಪಷ್ಟವಾಗಿ ತೋರಿಸುವಂತಹ, ಉತ್ತಮ ಬೆಳಕಿನಲ್ಲಿರುವ ಹತ್ತಿರದ ಫೋಟೋವನ್ನು ಮತ್ತೆ ತೆಗೆದುಕೊಳ್ಳಿ.)
            
            Treatment: Check for spots, wilting, mold, insects, or stem damage and try again in a few minutes.
            (ಚಿಕಿತ್ಸೆ: ಕಲೆಗಳು, ಒಣಗುವಿಕೆ, ಬೂಷ್ಟು, ಕೀಟಗಳು ಅಥವಾ ಕಾಂಡದ ಹಾನಿಯನ್ನು ಪರಿಶೀಲಿಸಿ ಮತ್ತು ಕೆಲವು ನಿಮಿಷಗಳ ನಂತರ ಮತ್ತೆ ಪ್ರಯತ್ನಿಸಿ.)
            
            Fertilizer/Nutrient support: Use a balanced fertilizer only after a confident diagnosis.
            (ರಸಗೊಬ್ಬರ/ಪೋಷಕಾಂಶ: ವಿಶ್ವಾಸಾರ್ಹ ರೋಗನಿರ್ಣಯದ ನಂತರವೇ ಸಮತೋಲಿತ ರಸಗೊಬ್ಬರವನ್ನು ಬಳಸಿ.)
        """.trimIndent()
    }
}
