package com.supremology.travelguide2.data.remote.dto

data class NearbyPlacesResponse(
    val query: GeoQuery
)

data class GeoQuery(
    val geosearch: List<GeoSearchItem>
)

data class GeoSearchItem(
    val pageid: Int,
    val title: String,
    val lat: Double,
    val lon: Double,
    val dist: Double
)
