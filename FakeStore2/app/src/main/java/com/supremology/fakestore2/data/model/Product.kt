package com.supremology.fakestore2.data.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String,
    val description: String,
    val category: String
)
