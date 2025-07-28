package com.supremology.travelguide2.data.remote.dto

data class SuggestResponse(
    val features: List<SuggestFeature>
)

data class SuggestFeature(
    val properties: SuggestProperties
)

data class SuggestProperties(
    val name: String,
    val xid: String
)
