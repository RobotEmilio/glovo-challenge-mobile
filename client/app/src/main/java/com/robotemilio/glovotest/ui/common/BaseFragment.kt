package com.robotemilio.glovotest.ui.common

import com.robotemilio.glovotest.di.DaggerViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory : DaggerViewModelFactory
}