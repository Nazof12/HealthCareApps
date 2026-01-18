package com.App.healtcare.di

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HealthLifeApp : Application() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate() {
        super.onCreate()
        val processName = Application.getProcessName()
        Log.d("APP_INIT", "HealthLifeApp onCreate CALLED " )
        FirebaseApp.initializeApp(this)
    }
}