package com.supremology.travelguide2

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration


@HiltAndroidApp
class TravelGuideApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Required by osmdroid
        Configuration.getInstance().load(this,
            getSharedPreferences("osmdroid", Context.MODE_PRIVATE))
    }
}
