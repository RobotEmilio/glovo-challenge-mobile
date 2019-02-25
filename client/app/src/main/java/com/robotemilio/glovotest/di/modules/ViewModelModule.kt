package com.robotemilio.glovotest.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robotemilio.glovotest.ui.map.MapsActivityViewModel
import com.robotemilio.glovotest.di.DaggerViewModelFactory
import com.robotemilio.glovotest.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * This class provides will all necessary Viewmodels in the project
 */
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MapsActivityViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapsActivityViewModel) : ViewModel
}