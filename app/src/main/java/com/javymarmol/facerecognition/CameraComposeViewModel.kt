package com.javymarmol.facerecognition

import android.content.Context
import androidx.camera.core.ImageProxy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * Created by Heyner Javier Marmol @javymarmol on 16/08/23.
 **/
class CameraComposeViewModel(private val context: Context) : ViewModel() {

    private val cameraRepository: ICameraRepository = CameraRepository(context)
    private lateinit var dispatcher: CoroutineDispatcher
    var cameraX = mutableStateOf(cameraRepository)
        private set

    var uiStatus by mutableStateOf<CameraUiState>(CameraUiState.Loading)
        private set


    //lateinit var navHostController: NavHostController

    private val faceRecognition = mutableStateOf(listOf(FaceRecognition("", 0f)))


    init {
        startStatus()
    }

    private fun startStatus(){
        /*val userCurrent = loginRepository.getUser()
        userCurrent?.apply {
            viewModelScope.launch(dispatcher) {

                repository.getFaceSaved().collect{
                    Log.d("TAG", "startStatus: $it")
                    uiStatus = it
                }
            }
        }*/

    }
    fun recognizerImage(imageProxy: ImageProxy) {
        viewModelScope.launch {
            //faceRecognition.value = classifierRepository.recognizeImage(imageProxy)

            imageProxy.close()
        }
    }
    fun onUnCoverRequest(mlId: String, croquettes:Int) {
        /*viewModelScope.launch {
            repository.addDogToUser(mlId, croquettes * -1)
            withContext(Dispatchers.Main){
                Toast.makeText(contextApp, contextApp.getString(R.string.dog_reward), Toast.LENGTH_LONG).show()
            }
        }*/
    }

}