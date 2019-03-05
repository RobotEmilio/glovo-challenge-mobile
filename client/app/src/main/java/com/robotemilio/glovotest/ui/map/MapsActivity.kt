package com.robotemilio.glovotest.ui.map

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.ui.common.BaseActivity

class MapsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        val navFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        navFragment?.let { navHostFragment ->
            navHostFragment.childFragmentManager.primaryNavigationFragment?.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        }
    }

}
