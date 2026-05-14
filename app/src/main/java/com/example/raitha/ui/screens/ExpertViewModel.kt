package com.example.raitha.ui.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.raitha.BuildConfig
import com.example.raitha.data.repository.TipRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ExpertViewModel @Inject constructor(
    private val repository: TipRepository
) : ViewModel() {

    var analysisResult by mutableStateOf<String?>(null)
        private set

    var statusMessage by mutableStateOf("Capture a leaf photo to check for disease signs and get treatment guidance.")
        private set

    var isAnalyzing by mutableStateOf(false)
        private set

    fun captureAndAnalyze(context: Context, cameraController: LifecycleCameraController) {
        if (isAnalyzing) return

        isAnalyzing = true
        statusMessage = "Capturing image..."

        cameraController.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: androidx.camera.core.ImageProxy) {
                    try {
                        val bitmap = image.toBitmap()
                        image.close()
                        analyzeBitmap(bitmap)
                    } catch (_: Exception) {
                        image.close()
                        isAnalyzing = false
                        statusMessage = "Error processing image. Please try again."
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    isAnalyzing = false
                    statusMessage = "Unable to capture image. Please try again."
                }
            }
        )
    }

    private fun analyzeBitmap(bitmap: android.graphics.Bitmap) {
        viewModelScope.launch {
            statusMessage = "Analyzing image with AI..."
            val analysisBitmap = bitmap.downscaleForAnalysis()
            val outputStream = ByteArrayOutputStream()
            analysisBitmap.compress(Bitmap.CompressFormat.JPEG, 75, outputStream)
            val base64Image = android.util.Base64.encodeToString(outputStream.toByteArray(), android.util.Base64.NO_WRAP)

            val result = repository.analyzeDisease(base64Image, BuildConfig.GEMINI_API_KEY)
            val fallbackMessage = result.error ?: "Please retake the image in better light and try again."
            analysisResult = result.text ?: "AI analysis unavailable. $fallbackMessage"
            statusMessage = if (result.isFallback) {
                "Gemini is temporarily unavailable. Showing safe fallback guidance."
            } else if (result.text == null) {
                "AI response unavailable. ${result.error ?: "Check the Gemini API key or quota."}"
            } else {
                "Diagnosis complete."
            }
            isAnalyzing = false
        }
    }

    private fun android.graphics.Bitmap.downscaleForAnalysis(maxDimension: Int = 1280): Bitmap {
        val largestSide = maxOf(width, height)
        if (largestSide <= maxDimension) return this

        val scale = maxDimension.toFloat() / largestSide.toFloat()
        val targetWidth = (width * scale).toInt().coerceAtLeast(1)
        val targetHeight = (height * scale).toInt().coerceAtLeast(1)
        return Bitmap.createScaledBitmap(this, targetWidth, targetHeight, true)
    }
}
