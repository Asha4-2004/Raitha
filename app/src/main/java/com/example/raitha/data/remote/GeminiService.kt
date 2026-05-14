package com.example.raitha.data.remote

import com.example.raitha.BuildConfig
import com.example.raitha.data.repository.GeminiAnalysisResult
import com.example.raitha.data.repository.TipRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiService @Inject constructor(
    private val repository: TipRepository,
    private val geminiApiService: GeminiApiService
) {
    suspend fun generateDailyTip(crop: String, district: String, season: String): String? {
        return repository.getGeminiTip(crop, district, season, BuildConfig.GEMINI_API_KEY)
    }

    suspend fun analyzePlantDisease(imageBase64: String): GeminiAnalysisResult {
        return repository.analyzeDisease(imageBase64, BuildConfig.GEMINI_API_KEY)
    }

    suspend fun translateToKannada(text: String): String? {
        return try {
            val request = GeminiRequest(
                contents = listOf(
                    Content(
                        parts = listOf(
                            Part(
                                text = "Translate this farming guidance into natural Kannada in one short sentence: $text"
                            )
                        )
                    )
                )
            )
            geminiApiService.generateContent(BuildConfig.GEMINI_API_KEY, request)
                .candidates
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
}
