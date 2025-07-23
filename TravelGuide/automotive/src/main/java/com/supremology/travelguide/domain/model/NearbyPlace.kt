package com.supremology.travelguide.domain.model


data class NearbyPlace(
    val name: String,
    val lat: Double,
    val lon: Double,
    val xid: String
)
