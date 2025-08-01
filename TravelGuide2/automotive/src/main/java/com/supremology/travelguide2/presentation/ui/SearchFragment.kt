package com.supremology.travelguide2.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supremology.travelguide2.R
import com.supremology.travelguide2.automotive.location.LocationTracker
import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.presentation.adapter.SearchAdapter
import com.supremology.travelguide2.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    @Inject
    lateinit var locationTracker: LocationTracker
    private lateinit var sourceInput: EditText
    private lateinit var destinationInput: EditText
    private lateinit var goButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SearchAdapter
    private var useCurrentLocationAsSource = true

    private var selectingSource = true
    private var selectedSource: Place? = null
    private var selectedDestination: Place? = null

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        sourceInput = view.findViewById(R.id.source_input)
        destinationInput = view.findViewById(R.id.destination_input)
        goButton = view.findViewById(R.id.go_button)
        recyclerView = view.findViewById(R.id.suggestions_recycler)

        adapter = SearchAdapter { place ->
            if (selectingSource) {
                selectedSource = place
                sourceInput.setText(place.name)
            } else {
                selectedDestination = place
                destinationInput.setText(place.name)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        sourceInput.setOnFocusChangeListener { _, hasFocus ->
            selectingSource = hasFocus
        }

        destinationInput.setOnFocusChangeListener { _, hasFocus ->
            selectingSource = !hasFocus
        }

        sourceInput.addTextChangedListener {
            val text = it.toString()
            useCurrentLocationAsSource = text.isBlank()
            if (!useCurrentLocationAsSource && selectingSource) {
                viewModel.search(text)
            }
        }

        destinationInput.addTextChangedListener {
            if (!selectingSource) viewModel.search(it.toString())
        }

        viewModel.suggestions.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        goButton.setOnClickListener {
            val dest = selectedDestination
            if (dest == null) {
                Toast.makeText(requireContext(), "Please select a destination", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!useCurrentLocationAsSource) {
                val source = selectedSource
                if (source == null) {
                    Toast.makeText(requireContext(), "Please select a source or leave it empty for current location", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val bundle = Bundle().apply {
                    putString("source_name", source.name)
                    putDouble("source_lat", source.lat)
                    putDouble("source_lon", source.lon)
                    putString("dest_name", dest.name)
                    putDouble("dest_lat", dest.lat)
                    putDouble("dest_lon", dest.lon)
                }
                findNavController().navigate(R.id.action_searchFragment_to_mapFragment, bundle)

            } else {
                // 🔄 Handle current location asynchronously
                lifecycleScope.launch {
                    val location = locationTracker.getCurrentLocation()
                    if (location != null) {
                        val bundle = Bundle().apply {
                            putString("source_name", "You")
                            putDouble("source_lat", location.latitude)
                            putDouble("source_lon", location.longitude)
                            putString("dest_name", dest.name)
                            putDouble("dest_lat", dest.lat)
                            putDouble("dest_lon", dest.lon)
                        }
                        findNavController().navigate(R.id.action_searchFragment_to_mapFragment, bundle)
                    } else {
                        Toast.makeText(requireContext(), "Unable to get current location", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return view
    }
}
