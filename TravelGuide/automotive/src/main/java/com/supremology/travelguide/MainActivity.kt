package com.supremology.travelguide

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.supremology.travelguide.presentation.ui.OsmFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Request permission
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ), 0)



        // Load the OSM map fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, OsmFragment())
            .commit()
    }
}
