package com.supremology.travelguide2.utils

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory

data class Gpx(
    val tracks: List<Track>
)

data class Track(
    val segments: List<TrackSegment>
)

data class TrackSegment(
    val trackPoints: List<TrackPoint>
)

data class TrackPoint(
    val latitude: Double,
    val longitude: Double
)

class GpxParser {
    fun parse(inputStream: java.io.InputStream): Gpx {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(inputStream, null)

        val tracks = mutableListOf<Track>()
        var segments = mutableListOf<TrackSegment>()
        var points = mutableListOf<TrackPoint>()

        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (parser.name) {
                "trkpt" -> {
                    val lat = parser.getAttributeValue(null, "lat").toDouble()
                    val lon = parser.getAttributeValue(null, "lon").toDouble()
                    points.add(TrackPoint(lat, lon))
                }
                "trkseg" -> {
                    if (points.isNotEmpty()) {
                        segments.add(TrackSegment(points.toList()))
                        points.clear()
                    }
                }
                "trk" -> {
                    if (segments.isNotEmpty()) {
                        tracks.add(Track(segments.toList()))
                        segments.clear()
                    }
                }
            }
            eventType = parser.next()
        }

        return Gpx(tracks)
    }
}
