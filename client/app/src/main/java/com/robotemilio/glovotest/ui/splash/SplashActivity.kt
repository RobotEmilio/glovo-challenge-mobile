package com.robotemilio.glovotest.ui.splash

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.extensions.toast
import com.robotemilio.glovotest.ui.common.BaseActivity
import com.robotemilio.glovotest.ui.map.MapsActivity

class SplashActivity : BaseActivity() {

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        checkPermissions()
    }

    private fun onGPSPermissionAccepted() {
        toast("GPS granted")
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onGPSPermissionDenied() {
        toast("GPS denied")
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            onGPSPermissionAccepted()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        }
    }

    private fun showPermissionExplanationDialog() {
        PermissionRationaleDialog(context = this,
            onDeny = { onGPSPermissionDenied() },
            onRetry = { checkPermissions() }).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                val isGpsPermissionGranted =
                    grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED

                if (isGpsPermissionGranted) {
                    onGPSPermissionAccepted()
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, ACCESS_FINE_LOCATION)) {
                        showPermissionExplanationDialog()
                    } else {
                        onGPSPermissionDenied()
                    }
                }
            }
        }
    }

}