package com.supremology.travelguide2.data.remote.dto

data class CoordinatesResponse(
    val query: CoordPages
)

data class CoordPages(
    val pages: Map<String, CoordPage>
)

data class CoordPage(
    val pageid: Int,
    val title: String,
    val coordinates: List<Coordinate>?
)

data class Coordinate(
    val lat: Double,
    val lon: Double
)
