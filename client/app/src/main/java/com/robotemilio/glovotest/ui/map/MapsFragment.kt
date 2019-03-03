package com.robotemilio.glovotest.ui.map

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.PolygonOptions
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.domain.exception.CustomException
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import com.robotemilio.glovotest.extensions.getViewModel
import com.robotemilio.glovotest.extensions.logError
import com.robotemilio.glovotest.extensions.observe
import com.robotemilio.glovotest.ui.common.BaseFragment

class MapsFragment : BaseFragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var mapViewModel: MapsActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap(view, savedInstanceState)
    }

    private fun initMap(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.map)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync { googleMap ->
            mMap = googleMap
            initViewModel()
        }
    }

    private fun initViewModel() {
        mapViewModel = getViewModel(activity as AppCompatActivity, viewModelFactory)

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

        cities?.forEach(::drawPolygon)
    }

    private fun onCountriesReceived(countries: List<Country>?) {
        Log.d("ANTONIO", countries.toString())
    }

    private fun drawPolygon(city: City) {
        city.workingArea.forEach {
            if (it.points.size > 2) {
                activity?.let { activity ->
                    mMap.addPolygon(
                        PolygonOptions()
                            .fillColor(ContextCompat.getColor(activity, R.color.transparentRed))
                            .strokeColor(ContextCompat.getColor(activity, R.color.lessTransparentRed))
                            .addAll(it.points)
                            .add(it.points[0])
                    )
                }
            }
        }
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
            CustomException.Code.NETWORK_ERROR -> {
                Toast.makeText(activity, "Network error", Toast.LENGTH_SHORT).show()
            }
            CustomException.Code.UNKNOWN -> {
                logError(customException, true)
            }
        }
    }

}