package com.supremology.fakestore2.data.remote

import com.supremology.fakestore2.data.model.Product
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}
