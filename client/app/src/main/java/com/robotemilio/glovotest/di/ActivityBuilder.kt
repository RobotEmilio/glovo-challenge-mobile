package com.robotemilio.glovotest.di

import com.robotemilio.glovotest.ui.map.MapsActivity
import com.robotemilio.glovotest.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This class injects all activities in project
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindMapsActivity() : MapsActivity

    @ContributesAndroidInjector
    abstract fun bindSplashActivity() : SplashActivity

}