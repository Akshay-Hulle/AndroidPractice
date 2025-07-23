package com.supremology.travelguide.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import com.supremology.travelguide.R
import com.supremology.travelguide.data.location.LocationTracker
import com.supremology.travelguide.presentation.viewmodel.OsmViewModel
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import java.io.File

@AndroidEntryPoint
class OsmFragment : Fragment() {

    private lateinit var mapView: MapView
    private val viewModel: OsmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_osm, container, false)
        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            setupObservers(map)
        }
        return view
    }

    private fun setupObservers(map: MapboxMap) {
        viewModel.fetchNearbyPlaces()

        lifecycleScope.launchWhenStarted {
            viewModel.nearbyPlaces.collect { places ->
                for (place in places) {
                    val markerOptions = MarkerOptions()
                        .position(LatLng(place.lat, place.lon))
                        .title(place.name)
                    map.addMarker(markerOptions)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().setUserAgentValue(requireContext().packageName)
        Configuration.getInstance().osmdroidBasePath = File(requireContext().filesDir, "osmdroid")
        Configuration.getInstance().osmdroidTileCache = File(Configuration.getInstance().osmdroidBasePath, "tile")


        mapView = view.findViewById(R.id.osmMap)
        mapView.setMultiTouchControls(true)
        mapView.setTileSource(TileSourceFactory.MAPNIK)

        val locationTracker = LocationTracker(requireContext())

        lifecycleScope.launch {
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val currentPoint = GeoPoint(location.latitude, location.longitude)
                mapView.controller.setZoom(16.0)
                mapView.controller.setCenter(currentPoint)

                val marker = Marker(mapView)
                marker.position = currentPoint
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                marker.title = "You are here"
                mapView.overlays.add(marker)
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
