package com.supremology.travelguide2.domain.usecase

import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.domain.repository.PlaceRepository
import javax.inject.Inject

class SearchDestinationUseCase @Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator fun invoke(query: String): List<Place> {
        return repository.searchPlaces(query)
    }
}
