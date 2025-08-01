package com.supremology.travelguide2.domain.usecase

import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.domain.repository.PlaceRepository
import javax.inject.Inject

class GetNearbyPlacesUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator fun invoke(
        lat: Double,
        lon: Double
    ): List<Place> {
        return repository.getNearbyPlaces(lat, lon)
    }
}
