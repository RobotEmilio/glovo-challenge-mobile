package com.robotemilio.glovotest.ui.map

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.domain.exception.CustomException
import com.robotemilio.glovotest.domain.exception.CustomException.Code.NETWORK_ERROR
import com.robotemilio.glovotest.domain.exception.CustomException.Code.UNKNOWN
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import com.robotemilio.glovotest.extensions.getViewModel
import com.robotemilio.glovotest.extensions.logError
import com.robotemilio.glovotest.extensions.observe
import com.robotemilio.glovotest.ui.common.BaseActivity

class MapsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    }



}
