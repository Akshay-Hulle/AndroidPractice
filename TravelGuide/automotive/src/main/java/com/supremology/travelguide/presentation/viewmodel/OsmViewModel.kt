package com.supremology.travelguide.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supremology.travelguide.data.location.LocationTracker
import com.supremology.travelguide.domain.model.NearbyPlace
import com.supremology.travelguide.domain.repository.NearbyPlacesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OsmViewModel @Inject constructor(
    private val repository: NearbyPlacesRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _nearbyPlaces = MutableStateFlow<List<NearbyPlace>>(emptyList())
    val nearbyPlaces: StateFlow<List<NearbyPlace>> = _nearbyPlaces

    fun fetchNearbyPlaces() {
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val places = repository.getNearbyPlaces(location.latitude, location.longitude)
                _nearbyPlaces.value = places
            }
        }
    }
}

