package com.javymarmol.facerecognition

import android.content.Context
import android.util.Log
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.javymarmol.facerecognition.ICameraRepository
import java.util.concurrent.ExecutorService

/**
 * Created by Heyner Javier Marmol @javymarmol on 16/08/23.
 **/
class CameraRepository constructor(private val context: Context) : ICameraRepository {
    private lateinit var cameraExecutors: ExecutorService
    private lateinit var owner: LifecycleOwner

    override fun startCameraPreviewView(recognizerImage: (image: ImageProxy) -> Unit): PreviewView {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        val previewView = PreviewView(context)

        previewView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->

            val preview = Preview.Builder().build().also {
                it.targetRotation = Surface.ROTATION_90
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            val imageCapture = ImageCapture.Builder()
                .setTargetRotation(Surface.ROTATION_90)
                .build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setTargetRotation(Surface.ROTATION_90)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutors) { imageProxy ->
                recognizerImage(imageProxy)
            }

            val camSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                cameraProviderFuture.get().unbindAll()
                cameraProviderFuture.get().bindToLifecycle(
                    owner,
                    camSelector,
                    preview,
                    imageAnalysis,
                    imageCapture
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("TAG", "CameraX: $e")
            }
        }

        return previewView
    }
}