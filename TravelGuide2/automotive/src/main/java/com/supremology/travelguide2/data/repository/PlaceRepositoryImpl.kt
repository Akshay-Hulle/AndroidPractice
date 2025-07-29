package com.supremology.travelguide2.data.repository

import android.content.Context
import com.supremology.travelguide2.data.remote.WikipediaApiService
import com.supremology.travelguide2.data.remote.mapper.toPlace
import com.supremology.travelguide2.data.remote.mapper.updatePlace
import com.supremology.travelguide2.domain.model.Place
import com.supremology.travelguide2.domain.repository.PlaceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PlaceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PlaceRepository {

    override suspend fun searchPlaces(query: String): List<Place> {
        return try {
            val response = WikipediaApiService.api.searchPlaces(query = query)
            val basicResults = response.query.search

            val pageIds = basicResults.joinToString("|") { it.pageid.toString() }

            val coordMap = try {
                WikipediaApiService.api.getPlaceCoordinates(pageIds= pageIds).query.pages
            } catch (e: Exception) {
                emptyMap()
            }

            basicResults.map { result ->
                val coord = coordMap[result.pageid.toString()]?.coordinates?.firstOrNull()
                Place(
                    id = result.pageid,
                    name = result.title,
                    lat = coord?.lat ?: 0.0,
                    lon = coord?.lon ?: 0.0,
                    desc = result.snippet,
                    imageUrl = "",
                    category = ""
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    override suspend fun getNearbyPlaces(lat: Double, lon: Double): List<Place> {
        val coord = "$lat|$lon"
        val geoResponse = WikipediaApiService.api.getNearbyPlaces(coord = coord)

        val basicPlaces = geoResponse.query.geosearch.map { it.toPlace() }

        val places = basicPlaces.map { place ->
            try {
                val detailRes = WikipediaApiService.api.getPlaceDetails(pageId = place.id)
                val detail = detailRes.query.pages.values.first()
                detail.updatePlace(place)
            } catch (e: Exception) {
                place
            }
        }

        return places
    }
}

