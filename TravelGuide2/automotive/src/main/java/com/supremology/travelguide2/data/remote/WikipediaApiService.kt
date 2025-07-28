package com.supremology.travelguide2.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.supremology.travelguide2.data.remote.api.WikipediaApi

object WikipediaApiService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://en.wikipedia.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: WikipediaApi = retrofit.create(WikipediaApi::class.java)
}
