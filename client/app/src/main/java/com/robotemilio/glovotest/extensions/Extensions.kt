package com.robotemilio.glovotest.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.robotemilio.glovotest.di.DaggerViewModelFactory

inline fun <reified T : ViewModel> getViewModel(
    activity: AppCompatActivity,
    factory: DaggerViewModelFactory
): T {
    return ViewModelProviders.of(activity, factory)[T::class.java]
}

inline fun <reified T : ViewModel> getViewModel(fragment: Fragment): T {
    return ViewModelProviders.of(fragment)[T::class.java]
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}