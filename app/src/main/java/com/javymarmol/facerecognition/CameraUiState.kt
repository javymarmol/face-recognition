package com.javymarmol.facerecognition

/**
 * Created by Heyner Javier Marmol @javymarmol on 16/08/23.
 **/
sealed interface CameraUiState {

    object Loaded: CameraUiState

    object Loading: CameraUiState

    data class Success(val data: Throwable) : CameraUiState

    data class Error(val message: String) : CameraUiState
}
