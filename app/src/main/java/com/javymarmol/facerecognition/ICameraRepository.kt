package com.javymarmol.facerecognition

import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView

/**
 * Created by Heyner Javier Marmol @javymarmol on 16/08/23.
 **/
interface ICameraRepository {
    fun startCameraPreviewView(recognizerImage:(image: ImageProxy)->Unit): PreviewView
}