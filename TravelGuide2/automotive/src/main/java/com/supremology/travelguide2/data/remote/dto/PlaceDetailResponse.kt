package com.supremology.travelguide2.data.remote.dto

data class PlaceDetailResponse(
    val query: DetailQuery
)

data class DetailQuery(
    val pages: Map<String, PageDetail>
)

data class PageDetail(
    val pageid: Int,
    val title: String,
    val extract: String?,
    val original: OriginalImage?
)

data class OriginalImage(
    val source: String
)
