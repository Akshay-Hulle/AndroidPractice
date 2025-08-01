package com.supremology.travelguide2.presentation.ui

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.supremology.travelguide2.R
import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.presentation.adapter.NearbyPlacesAdapter
import com.supremology.travelguide2.presentation.viewmodel.MapViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@AndroidEntryPoint
class MapFragment : Fragment() {

    private lateinit var mapView: MapView
    private val viewModel: MapViewModel by viewModels()
    private lateinit var adapter: NearbyPlacesAdapter

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

    private fun zoomToBounds(sourcePoint: GeoPoint, destPoint: GeoPoint) {
        val boundingBox = BoundingBox.fromGeoPoints(listOf(sourcePoint, destPoint))
        val padding = 0.01
        val minZoom = 15.0

        val adjustedBoundingBox = BoundingBox(
            boundingBox.latNorth + padding,
            boundingBox.lonEast + padding,
            boundingBox.latSouth - padding,
            boundingBox.lonWest - padding
        )

        mapView.zoomToBoundingBox(adjustedBoundingBox, true)
        if (mapView.zoomLevelDouble < minZoom) {
            mapView.controller.setZoom(minZoom)
        }
        mapView.controller.setCenter(boundingBox.centerWithDateLine)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        arguments?.let {
            val sourceLat = it.getDouble("source_lat", 0.0)
            val sourceLon = it.getDouble("source_lon", 0.0)
            val sourceName = it.getString("source_name") ?: "Source"

            val destLat = it.getDouble("dest_lat", 0.0)
            val destLon = it.getDouble("dest_lon", 0.0)
            val destName = it.getString("dest_name") ?: "Destination"

            if (sourceLat != 0.0 && sourceLon != 0.0 && destLat != 0.0 && destLon != 0.0) {
                val sourcePoint = GeoPoint(sourceLat, sourceLon)
                val destPoint = GeoPoint(destLat, destLon)

                addMarker(sourcePoint, sourceName)
                addMarker(destPoint, destName)
                zoomToBounds(sourcePoint, destPoint)
            }
        }
        Configuration.getInstance().load(requireContext(), PreferenceManager.getDefaultSharedPreferences(requireContext()))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.nearbyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.places.observe(viewLifecycleOwner) { places ->
            adapter = NearbyPlacesAdapter(places) { place ->
                showPlaceDetailsDialog(place)
            }
            recyclerView.adapter = adapter
        }

        // Get current location
        viewModel.currentLocation.observe(viewLifecycleOwner) { location ->
            location?.let {
                viewModel.fetchNearbyPlaces(it.latitude, it.longitude)
            }
        }

        viewModel.loadCurrentLocation()
        view.findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewModel.places.observe(viewLifecycleOwner) { places ->
            adapter = NearbyPlacesAdapter(places) { place ->
                showPlaceDetailsDialog(place)
            }
            recyclerView.adapter = adapter
        }

    }

    private fun showPlaceDetailsDialog(place: Place) {
        val dialogView = layoutInflater.inflate(R.layout.place_details, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.8).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER)

        val imageView = dialogView.findViewById<ImageView>(R.id.placeImage)
        val titleText = dialogView.findViewById<TextView>(R.id.placeTitle)
        val categoryText = dialogView.findViewById<TextView>(R.id.placeCategory)
        val descriptionText = dialogView.findViewById<TextView>(R.id.placeDescription)
        val closeBtn = dialogView.findViewById<ImageView>(R.id.closeButton)

        imageView.load(place.imageUrl) {
            placeholder(R.drawable.ic_placeholder)
            error(R.drawable.ic_placeholder)
        }
        titleText.text = place.name
        categoryText.text = place.category
        descriptionText.text = place.desc

        closeBtn.setOnClickListener { dialog.dismiss() }

        dialog.show()
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
