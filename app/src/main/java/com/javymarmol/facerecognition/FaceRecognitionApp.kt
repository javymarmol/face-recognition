package com.javymarmol.facerecognition

import android.app.Application
import android.content.Context

/**
 * Created by Heyner Javier Marmol @javymarmol on 16/08/23.
 **/
class FaceRecognitionApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: FaceRecognitionApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = FaceRecognitionApp.applicationContext()
    }
}