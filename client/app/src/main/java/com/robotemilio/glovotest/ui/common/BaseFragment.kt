package com.robotemilio.glovotest.ui.common

import android.widget.Toast
import com.robotemilio.glovotest.di.DaggerViewModelFactory
import com.robotemilio.glovotest.domain.exception.CustomException
import com.robotemilio.glovotest.extensions.logError
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory : DaggerViewModelFactory

    protected fun handleErrors(throwable: Throwable?) {
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