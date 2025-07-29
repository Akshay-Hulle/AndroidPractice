package com.supremology.travelguide2.data.remote.api

import com.supremology.travelguide2.data.remote.dto.CoordinatesResponse
import com.supremology.travelguide2.data.remote.dto.NearbyPlacesResponse
import com.supremology.travelguide2.data.remote.dto.PlaceDetailResponse
import com.supremology.travelguide2.data.remote.dto.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WikipediaApi {

    @GET("w/api.php")
    suspend fun searchPlaces(
        @Query("action") action: String = "query",
        @Query("list") list: String = "search",
        @Query("srsearch") query: String,
        @Query("format") format: String = "json",
        @Query("origin") origin: String = "*"
    ): SearchResponse

    @GET("w/api.php")
    suspend fun getPlaceCoordinates(
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "coordinates",
        @Query("pageids") pageIds: String,
        @Query("format") format: String = "json",
        @Query("origin") origin: String = "*"
    ): CoordinatesResponse

    @GET("w/api.php")
    suspend fun getNearbyPlaces(
        @Query("action") action: String = "query",
        @Query("list") list: String = "geosearch",
        @Query("gscoord") coord: String,
        @Query("gsradius") radius: Int = 10000,
        @Query("gslimit") limit: Int = 10,
        @Query("format") format: String = "json"
    ): NearbyPlacesResponse

    @GET("w/api.php")
    suspend fun getPlaceDetails(
        @Query("action") action: String = "query",
        @Query("prop") prop: String = "extracts|pageimages",
        @Query("pageids") pageId: Int,
        @Query("format") format: String = "json",
        @Query("exintro") exintro: Int = 1,
        @Query("explaintext") explaintext: Int = 1,
        @Query("piprop") piprop: String = "original"
    ): PlaceDetailResponse
}
