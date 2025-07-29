package com.supremology.travelguide2.data.remote.mapper

import com.supremology.travelguide2.data.remote.dto.GeoSearchItem
import com.supremology.travelguide2.data.remote.dto.PageDetail
import com.supremology.travelguide2.data.remote.dto.SearchResult
import com.supremology.travelguide2.domain.model.Place

fun GeoSearchItem.toPlace(): Place {
    return Place(
        id = this.pageid,
        name = this.title,
        lat = this.lat,
        lon = this.lon,
        desc = "",
        imageUrl = "",
        category = ""
    )
}

fun SearchResult.toPlace(): Place {

    return Place(
        id = this.pageid,
        name = this.title,
        lat = 0.0,
        lon = 0.0,
        desc = this.snippet ?: "",
        imageUrl = "",
        category = ""
    )
}

fun PageDetail.updatePlace(place: Place): Place {
    return place.copy(
        desc = extract ?: "",
        imageUrl = original?.source
    )
}

fun String.removeHtmlTags(): String {
    return this.replace(Regex("<.*?>"), "")
}
