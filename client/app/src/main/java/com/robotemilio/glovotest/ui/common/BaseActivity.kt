package com.robotemilio.glovotest.ui.common

import com.robotemilio.glovotest.di.DaggerViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

open class BaseActivity : DaggerAppCompatActivity() {
    @Inject lateinit var viewModelFactory : DaggerViewModelFactory
}