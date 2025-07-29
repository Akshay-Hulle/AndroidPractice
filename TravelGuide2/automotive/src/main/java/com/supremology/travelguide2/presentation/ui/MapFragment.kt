package com.supremology.travelguide2.presentation.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // ✅ Check if lat/lon were passed from SearchFragment
        arguments?.let {
            val sourceLat = it.getDouble("source_lat", 0.0)
            val sourceLon = it.getDouble("source_lon", 0.0)
            val sourceName = it.getString("source_name") ?: "Source"

            val destLat = it.getDouble("dest_lat", 0.0)
            val destLon = it.getDouble("dest_lon", 0.0)
            val destName = it.getString("dest_name") ?: "Destination"

            if (sourceLat != 0.0 && sourceLon != 0.0) {
                val sourcePoint = GeoPoint(sourceLat, sourceLon)
                addMarker(sourcePoint, sourceName, clear = true)
            }

            if (destLat != 0.0 && destLon != 0.0) {
                val destPoint = GeoPoint(destLat, destLon)
                addMarker(destPoint, destName, clear = false)
                mapView.controller.setCenter(destPoint)
            }
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
