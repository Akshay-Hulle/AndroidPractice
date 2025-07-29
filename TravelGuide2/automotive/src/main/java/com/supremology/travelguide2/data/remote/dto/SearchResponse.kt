package com.supremology.travelguide2.data.remote.dto

data class SearchResponse(
    val query: SearchQuery
)

data class SearchQuery(
    val search: List<SearchResult>
)

data class SearchResult(
    val pageid: Int,
    val title: String,
    val snippet: String,
)