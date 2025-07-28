package com.supremology.travelguide2.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.domain.usecase.GetNearbyPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase
) : ViewModel() {

    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> = _places

    fun fetchNearbyPlaces(lat: Double, lon: Double) {
        viewModelScope.launch {
            val result = getNearbyPlacesUseCase(lat, lon)
            _places.value = result
        }
    }
}
