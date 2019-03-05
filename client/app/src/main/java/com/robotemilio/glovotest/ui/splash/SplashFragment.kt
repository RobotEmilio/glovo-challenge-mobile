package com.robotemilio.glovotest.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.domain.exception.CustomExceptionWrapper
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.extensions.getViewModel
import com.robotemilio.glovotest.extensions.observe
import com.robotemilio.glovotest.ui.common.BaseFragment
import com.robotemilio.glovotest.ui.map.MapsFragment.Companion.SELECTED_CITY

class SplashFragment : BaseFragment() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var mLocationClient: FusedLocationProviderClient
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        initViewModel()
        checkPermissions()
    }

    private fun initViewModel() {
        splashViewModel = getViewModel(activity as AppCompatActivity, viewModelFactory)

        observe(splashViewModel.cityInRange, ::onRangeResultReceived)
        observe(splashViewModel.errorsReceived, ::handleErrors)
    }

    private fun onRangeResultReceived(city: City?) {
        if (city != null) {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.showMaps, bundleOf(SELECTED_CITY to city))
        } else {
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                .navigate(R.id.showCountryList)
        }
    }

    @SuppressLint("MissingPermission")
    private fun onGPSPermissionAccepted() {
        /*I took the liberty on supressing the lint error because we know
        that this method gets called only if we have the permission*/
        mLocationClient.lastLocation
            .addOnSuccessListener {
                splashViewModel.checkInRange(LatLng(it.latitude, it.longitude))
            }

    }

    private fun onGPSPermissionDenied() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.showCountryList)
    }

    private fun checkPermissions() {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                onGPSPermissionAccepted()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun showPermissionExplanationDialog() {
        PermissionRationaleDialog(context = requireContext(),
            onDeny = { onGPSPermissionDenied() },
            onRetry = { checkPermissions() }).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            val isGpsPermissionGranted =
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

            if (isGpsPermissionGranted) {
                onGPSPermissionAccepted()
            } else {

                val shouldShowPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )

                if (shouldShowPermissionRationale) {
                    showPermissionExplanationDialog()
                } else {
                    onGPSPermissionDenied()
                }

            }
        }
    }

    override fun handleNetworkError(customExceptionWrapper: CustomExceptionWrapper) {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.noServerFragment)
    }

}