package com.supremology.fakestore.data.repository

import com.supremology.fakestore.data.model.Product
import com.supremology.fakestore.data.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ProductRepository {

    private val api: ApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun fetchProducts(): List<Product> = api.getProducts()
}