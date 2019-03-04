package com.robotemilio.glovotest.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotemilio.glovotest.ui.map.MapsViewModel
import com.robotemilio.glovotest.di.DaggerViewModelFactory
import com.robotemilio.glovotest.di.ViewModelKey
import com.robotemilio.glovotest.ui.countrylist.CountriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This class provides will all necessary Viewmodels in the project
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesViewModel(viewModel: CountriesViewModel): ViewModel
}