package com.supremology.fakestore2.repository

import com.supremology.fakestore2.data.model.Product
import com.supremology.fakestore2.data.remote.ProductApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductRepository {
    private val api: ProductApiService = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProductApiService::class.java)

    suspend fun getProducts(): List<Product> = api.getProducts()
}
