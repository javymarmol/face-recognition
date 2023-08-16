package com.javymarmol.facerecognition

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Camera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.javymarmol.facerecognition.ui.theme.Purple80
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by Heyner Javier Marmol @javymarmol on 16/08/23.
 **/
@ExperimentalCoroutinesApi
@Composable
fun CameraCompose() {
    // Permission Camera
    val context = LocalContext.current
    val cameraViewModel = CameraComposeViewModel(context)
    val REQUIRED_PERMISSIONS =
        mutableListOf(
            Manifest.permission.CAMERA,
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
    var hasCamPermission by remember {
        mutableStateOf(
            REQUIRED_PERMISSIONS.all {
                ContextCompat.checkSelfPermission(context, it) ==
                        PackageManager.PERMISSION_GRANTED
            })
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { granted ->
            hasCamPermission = granted.size == 2
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }


    Surface(modifier = Modifier.fillMaxSize()) {
        if (hasCamPermission) {
            Column(Modifier.fillMaxSize()) {
                AndroidView(modifier = Modifier.fillMaxSize(),
                    factory = {
                        cameraViewModel.cameraX.value.startCameraPreviewView {
                            cameraViewModel.recognizerImage(it)
                        }
                    }
                )
            }


        }
    }

}

@Composable
fun ButtonCamera(enabled: Boolean = true, onCaptureClick: () -> Unit) {
    FloatingActionButton(
        onClick = { if(enabled){
            onCaptureClick()
        } },
        containerColor = Color.Transparent,
        modifier = Modifier.background(
            Color.Transparent
        )
    ) {
        if (enabled) {
            Icon(imageVector = Icons.Sharp.Camera,
                contentDescription = "Capture face",
                tint = Purple80,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp))
        } else {
            Icon(
                imageVector = Icons.Sharp.Camera,
                contentDescription = "Capture face",
                tint = Color.Gray,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )
        }
    }


}
