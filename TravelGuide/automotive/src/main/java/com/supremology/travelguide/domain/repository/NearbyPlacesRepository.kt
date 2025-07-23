package com.supremology.travelguide.domain.repository

import com.supremology.travelguide.domain.model.NearbyPlace

interface NearbyPlacesRepository {
    suspend fun getNearbyPlaces(lat: Double, lon: Double): List<NearbyPlace>
}
