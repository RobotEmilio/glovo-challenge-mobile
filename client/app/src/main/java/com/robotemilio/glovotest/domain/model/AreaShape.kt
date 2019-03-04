package com.robotemilio.glovotest.domain.model

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class AreaShape(val points: List<LatLng>) : Serializable