package com.robotemilio.glovotest.domain.model

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterItem

class MapPoint(
    val city: City,
    private val title: String? = null,
    private val snippet: String? = null
) : ClusterItem {

    override fun getSnippet(): String? {
        return snippet
    }

    override fun getTitle(): String? {
        return title
    }

    override fun getPosition(): LatLng {
        return city.center
    }

    fun getBounds() : LatLngBounds {
        return city.bounds
    }
}