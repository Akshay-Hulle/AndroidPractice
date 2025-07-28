package com.supremology.travelguide2.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.supremology.travelguide2.R
import com.supremology.travelguide2.presentation.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var mapView: MapView
    private val viewModel: MapViewModel by viewModels()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private fun addMarker(geoPoint: GeoPoint, title: String, clear: Boolean = false) {
        if (clear) mapView.overlays.clear()
        val marker = Marker(mapView).apply {
            position = geoPoint
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            this.title = title
        }
        mapView.overlays.add(marker)
        mapView.invalidate()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)
        val controller = mapView.controller
        controller.setZoom(15.0)

        // Show toast to confirm fragment loaded
        Toast.makeText(requireContext(), "MapFragment loaded", Toast.LENGTH_SHORT).show()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // ✅ Check if lat/lon were passed from SearchFragment
        arguments?.let {
            val name = it.getString("place_name") ?: "Selected Place"
            val lat = it.getDouble("lat", 0.0)
            val lon = it.getDouble("lon", 0.0)

            Toast.makeText(requireContext(), "Selected: $name ($lat, $lon)", Toast.LENGTH_LONG).show()

            if (lat != 0.0 && lon != 0.0) {
                var geoPoint = GeoPoint(lat, lon)
                addMarker(geoPoint, name, clear = true)
                controller.setCenter(geoPoint)
                return view
            }
        }?: run {
            Toast.makeText(requireContext(), "No arguments passed", Toast.LENGTH_SHORT).show()
        }
        // No place passed, so show current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        val currentGeoPoint = GeoPoint(it.latitude, it.longitude)
                        addMarker(currentGeoPoint, "You are here", clear = false)
                        mapView.controller.setCenter(currentGeoPoint)
                    }
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1001)
        }
        return view
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1001 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    location?.let {
                        val currentGeoPoint = GeoPoint(it.latitude, it.longitude)
                        addMarker(currentGeoPoint, "You are here", clear = false)
                        mapView.controller.setCenter(currentGeoPoint)
                    }
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
}
