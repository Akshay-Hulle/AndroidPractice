package com.supremology.travelguide2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supremology.travelguide2.automotive.location.LocationTracker
import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.domain.usecase.GetNearbyPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> = _places

    private val _currentLocation = MutableLiveData<GeoPoint?>()
    val currentLocation: LiveData<GeoPoint?> = _currentLocation

    // ✅ Call this in Fragment to start location and nearby fetch
    fun loadCurrentLocation() {
        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            _currentLocation.value = location
            location?.let {
                fetchNearbyPlaces(it.latitude, it.longitude)
            }
        }
    }

    // ✅ Also exposed separately
    fun fetchNearbyPlaces(lat: Double, lon: Double) {
        viewModelScope.launch {
            val result = getNearbyPlacesUseCase(lat, lon)
            _places.value = result
        }
    }
}
