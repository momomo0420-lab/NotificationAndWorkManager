package com.example.notificationandworkmanager

import android.app.Application
import androidx.work.Configuration

class MyApplication : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration
            .Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
    }
}