package com.robotemilio.glovotest.ui.map

import com.robotemilio.glovotest.ui.countrylist.CountryListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MapsActivityFragmentProvider {

    @ContributesAndroidInjector
    abstract fun provideMapsFragment() : MapsFragment

    @ContributesAndroidInjector
    abstract fun provideCountriesList() : CountryListFragment

}