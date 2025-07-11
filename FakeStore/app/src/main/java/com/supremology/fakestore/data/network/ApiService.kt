package com.supremology.fakestore.data.network

import com.supremology.fakestore.data.model.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}