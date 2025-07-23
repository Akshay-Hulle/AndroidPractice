package com.supremology.travelguide.data.mapper

import com.supremology.travelguide.data.remote.dto.NearbyPlaceDto
import com.supremology.travelguide.domain.model.NearbyPlace

fun NearbyPlaceDto.toDomain(): NearbyPlace {
    return NearbyPlace(
        name = this.properties.name,
        lat = this.properties.point.lat,
        lon = this.properties.point.lon,
        xid = this.properties.xid
    )
}
