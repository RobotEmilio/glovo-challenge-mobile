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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.algo.Algorithm
import com.google.maps.android.clustering.algo.GridBasedAlgorithm
import com.google.maps.android.clustering.algo.NonHierarchicalDistanceBasedAlgorithm
import com.google.maps.android.clustering.algo.PreCachingAlgorithmDecorator
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.domain.exception.CustomException
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import com.robotemilio.glovotest.domain.model.MapPoint
import com.robotemilio.glovotest.extensions.getViewModel
import com.robotemilio.glovotest.extensions.logError
import com.robotemilio.glovotest.extensions.observe
import com.robotemilio.glovotest.ui.common.BaseFragment

class MapsFragment : BaseFragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var mapViewModel: MapsActivityViewModel
    private lateinit var mClusterManager: ClusterManager<MapPoint>

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
            setUpClusterManager()
            initViewModel()
        }
    }

    private fun setUpClusterManager() {
        mClusterManager = ClusterManager(activity, mMap)
        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)
    }

    private fun initViewModel() {
        mapViewModel = getViewModel(activity as AppCompatActivity, viewModelFactory)

        //Manage subscriptions
        observe(mapViewModel.countriesLiveData, ::onCountriesReceived)
        observe(mapViewModel.citiesLiveData, ::onCitiesReceived)
        observe(mapViewModel.currentCityLiveData, ::onCurrentCityChanged)
        observe(mapViewModel.errorsReceived, ::handleErrors)

        mapViewModel.getCityList()
    }

    private fun onCurrentCityChanged(city: City?) {
        Log.d("ANTONIO", city.toString())
    }

    private fun onCitiesReceived(cities: List<City>?) {

        cities?.forEach {
            drawPolygon(it)
            addClusterMarker(it)
        }
    }

    private fun addClusterMarker(city: City) {
        mClusterManager.apply {
            addItem(MapPoint(city, title = city.name, snippet = city.code))

            setOnClusterClickListener { cluster ->
                val builder = LatLngBounds.builder()
                cluster.items.forEach { point -> builder.include(point.position) }
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50))
                true
            }
            setOnClusterItemClickListener {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(it.getBounds(), 50))
                true
            }
        }
    }

    private fun onCountriesReceived(countries: List<Country>?) {
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