package com.robotemilio.glovotest.ui.common

import com.robotemilio.glovotest.R
import com.robotemilio.glovotest.di.DaggerViewModelFactory
import com.robotemilio.glovotest.domain.exception.CustomExceptionWrapper
import com.robotemilio.glovotest.extensions.logError
import com.robotemilio.glovotest.extensions.toast
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    protected open fun handleErrors(throwable: Throwable?) {
        throwable?.let {
            when (it) {
                is CustomExceptionWrapper -> handleCustomException(it)
                else -> logError(it, true)
            }
        }
    }

    protected open fun handleCustomException(customExceptionWrapper: CustomExceptionWrapper) {
        when (customExceptionWrapper.code) {
            CustomExceptionWrapper.Code.NETWORK_ERROR -> handleNetworkError(customExceptionWrapper)
            CustomExceptionWrapper.Code.UNKNOWN -> {
                logError(customExceptionWrapper, true)
            }
        }
    }

    protected open fun handleNetworkError(customExceptionWrapper: CustomExceptionWrapper) {
        activity?.toast(getString(R.string.network_error))
    }
}