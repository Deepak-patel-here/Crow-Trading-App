package com.deepakjetpackcompose.crowtradingapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TradingApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}