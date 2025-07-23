package com.supremology.travelguide.data.remote.api

import com.supremology.travelguide.data.remote.dto.NearbyPlaceDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NearbyPlacesApi {
    @GET("radius")
    suspend fun getNearbyPlaces(
        @Query("radius") radius: Int = 1000,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("apikey") apiKey: String
    ): NearbyPlacesResponse
}

data class NearbyPlacesResponse(
    val features: List<NearbyPlaceDto>
)