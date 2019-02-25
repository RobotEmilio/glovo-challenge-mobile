package com.robotemilio.glovotest

import com.robotemilio.glovotest.di.components.DaggerGlovoTestComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class GlovoTestApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerGlovoTestComponent.builder().application(this).build()
        component.inject(this)
        return component
    }

}