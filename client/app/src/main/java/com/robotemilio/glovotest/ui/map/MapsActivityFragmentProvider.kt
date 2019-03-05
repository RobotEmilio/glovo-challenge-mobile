package com.robotemilio.glovotest.ui.map

import com.robotemilio.glovotest.ui.countrylist.CountryListFragment
import com.robotemilio.glovotest.ui.splash.NoServerFragment
import com.robotemilio.glovotest.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapsActivityFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideMapsFragment() : MapsFragment

    @ContributesAndroidInjector
    abstract fun provideCountriesList() : CountryListFragment

    @ContributesAndroidInjector
    abstract fun provideSplashFragment() : SplashFragment

    @ContributesAndroidInjector
    abstract fun providesNoServerFragment() : NoServerFragment

}