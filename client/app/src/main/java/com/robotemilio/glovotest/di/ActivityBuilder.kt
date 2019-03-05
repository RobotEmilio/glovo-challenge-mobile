package com.robotemilio.glovotest.di

import com.robotemilio.glovotest.ui.map.MapsActivity
import com.robotemilio.glovotest.ui.map.MapsActivityFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This class injects all activities in project
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MapsActivityFragmentProvider::class])
    abstract fun bindMapsActivity() : MapsActivity

}