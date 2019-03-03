package com.robotemilio.glovotest.ui.map

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapsActivityFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideMapsFragment() : com.robotemilio.glovotest.ui.map.MapsFragment

}