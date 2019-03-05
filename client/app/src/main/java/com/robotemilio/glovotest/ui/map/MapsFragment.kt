package com.robotemilio.glovotest.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.clustering.ClusterManager
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.MapPoint
import com.robotemilio.glovotest.extensions.getViewModel
import com.robotemilio.glovotest.extensions.observe
import com.robotemilio.glovotest.ui.common.BaseFragment
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : BaseFragment() {

    companion object {
        const val SELECTED_CITY = "selected_city"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var mapViewModel: MapsViewModel
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
        mapView.getMapAsync { onMapLoaded(it) }
    }

    private fun onMapLoaded(googleMap: GoogleMap) {
        mMap = googleMap
        setUpClusterManager()
        initViewModel()

        arguments?.getSerializable(SELECTED_CITY)?.let {
            val city = it as? City
            city?.let { mapViewModel.getCityInfo(it) }
        }
    }

    private fun setUpClusterManager() {
        mClusterManager = ClusterManager(activity, mMap)
        mMap.setOnCameraIdleListener(mClusterManager)
        mMap.setOnMarkerClickListener(mClusterManager)
        mMap.setOnCameraMoveListener { hideInfo() }
    }

    private fun hideInfo() {
        motion_map_container.transitionToStart()
    }

    private fun initViewModel() {
        mapViewModel = getViewModel(activity as AppCompatActivity, viewModelFactory)

        //Manage subscriptions
        observe(mapViewModel.citiesLiveData, ::onCitiesReceived)
        observe(mapViewModel.currentCityLiveData, ::onCurrentCityChanged)
        observe(mapViewModel.errorsReceived, ::handleErrors)

        mapViewModel.getCityList()
    }

    private fun onCurrentCityChanged(city: City?) {
        city?.let(::showInfo)
    }

    private fun onCitiesReceived(cities: List<City>?) {
        mMap.clear()
        mClusterManager.clearItems()
        cities?.forEach {
            drawCityPolygons(it)
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
                mapViewModel.getCityInfo(it.city)
                true
            }
        }
    }

    private fun showInfo(city: City) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(city.bounds, 50))
        city_info_view.setData(city)
        motion_map_container.transitionToEnd()
    }

    private fun drawCityPolygons(city: City) {
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


    override fun onDestroyView() {
        mapView.onDestroy()
        mMap.clear()
        mClusterManager.clearItems()
        super.onDestroyView()
    }

}