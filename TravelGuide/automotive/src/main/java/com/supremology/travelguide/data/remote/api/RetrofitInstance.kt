package com.supremology.travelguide.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: NearbyPlacesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.opentripmap.com/0.1/en/places/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NearbyPlacesApi::class.java)
    }
}
