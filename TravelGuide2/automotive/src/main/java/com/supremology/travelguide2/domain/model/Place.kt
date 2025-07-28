package com.supremology.travelguide2.domain.model

data class Place(
    val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
    val desc: String,
    val imageUrl: String?,
    val category: String = ""
)
