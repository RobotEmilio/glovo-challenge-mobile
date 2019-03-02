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

class MapsActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var mapViewModel: MapsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        setViews()
        initViewModel()
    }

    private fun setViews() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    private fun initViewModel() {
        mapViewModel = getViewModel(this, viewModelFactory)

        //Manage subscriptions
        observe(mapViewModel.countriesLiveData, ::onCountriesReceived)
        observe(mapViewModel.citiesLiveData, ::onCitiesReceived)
        observe(mapViewModel.currentCityLiveData, ::onCurrentCityChanged)
        observe(mapViewModel.errorsReceived, ::handleErrors)

        mapViewModel.getCountryList()
        mapViewModel.getCityList()
    }

    private fun onCurrentCityChanged(city: City?) {
        Log.d("ANTONIO", city.toString())
    }

    private fun onCitiesReceived(cities: List<City>?) {
        Log.d("ANTONIO", cities.toString())

        cities?.forEach(::drawPolygon)

        mapViewModel.getCityInfo("BCN")
    }

    fun drawPolygon(city: City) {
        city.workingArea.forEach {
            if (it.points.size > 2) {
                mMap.addPolygon(
                    PolygonOptions()
                        .fillColor(ContextCompat.getColor(this, R.color.transparentRed))
                        .strokeColor(ContextCompat.getColor(this, R.color.lessTransparentRed))
                        .addAll(it.points)
                        .add(it.points[0])
                )
            }
        }
    }

    private fun onCountriesReceived(countries: List<Country>?) {
        Log.d("ANTONIO", countries.toString())
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun handleErrors(throwable: Throwable?) {
        throwable?.let {
            when (it) {
                is CustomException -> handleCustomException(it)
                else -> logError(it, true)
            }
        }
    }

    private fun handleCustomException(customException: CustomException) {
        when (customException.code) {
            NETWORK_ERROR -> {
                Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show()
            }
            UNKNOWN -> {
                logError(customException, true)
            }
        }
    }

}
