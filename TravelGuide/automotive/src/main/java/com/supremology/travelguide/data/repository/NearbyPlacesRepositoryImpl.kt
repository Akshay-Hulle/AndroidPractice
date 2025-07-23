package com.supremology.travelguide.data.repository

import com.supremology.travelguide.data.mapper.toDomain
import com.supremology.travelguide.data.remote.api.NearbyPlacesApi
import com.supremology.travelguide.domain.model.NearbyPlace
import com.supremology.travelguide.domain.repository.NearbyPlacesRepository

class NearbyPlacesRepositoryImpl(
    private val api: NearbyPlacesApi
) : NearbyPlacesRepository {
    override suspend fun getNearbyPlaces(lat: Double, lon: Double): List<NearbyPlace> {
        val response = api.getNearbyPlaces(lat = lat, lon = lon, apiKey = "YOUR_API_KEY")
        return response.features.map { it.toDomain() }
    }
}
