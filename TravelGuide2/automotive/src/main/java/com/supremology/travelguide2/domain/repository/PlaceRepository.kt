package com.supremology.travelguide2.domain.repository

import com.supremology.travelguide2.domain.model.Place

interface PlaceRepository {
    suspend fun searchPlaces(query: String): List<Place>
    suspend fun getNearbyPlaces(lat: Double, lon: Double): List<Place>
}
