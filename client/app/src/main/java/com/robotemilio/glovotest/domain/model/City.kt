package com.robotemilio.glovotest.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import java.io.Serializable

data class City(
    val countryCode: String,
    val name: String,
    var currency: String? = null,
    val code: String,
    var enabled: Boolean? = null,
    var timeZone: String? = null,
    val workingArea: List<AreaShape>,
    var languageCode: String? = null,
    var busy: Boolean? = null
) : Serializable {

    lateinit var center: LatLng
    lateinit var bounds: LatLngBounds

    init {
        compute(workingArea.map { it.points }.flatten())
    }

    private fun compute(points: List<LatLng>) {
        var west = 180.0
        var east = -180.0
        var north = -90.0
        var south = 90.0
        points.forEach {
            if (west > it.longitude) west = it.longitude
            if (east < it.longitude) east = it.longitude
            if (north < it.latitude) north = it.latitude
            if (south > it.latitude) south = it.latitude
        }
        center = LatLng((north + south) / 2.0, (west + east) / 2.0)
        bounds = LatLngBounds(LatLng(south, west), LatLng(north, east))
    }
}