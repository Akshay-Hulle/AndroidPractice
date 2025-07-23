package com.supremology.travelguide.data.remote.dto

data class NearbyPlaceDto(
    val properties: Properties
)

data class Properties(
    val xid: String,
    val name: String,
    val point: Point
)

data class Point(
    val lat: Double,
    val lon: Double
)
