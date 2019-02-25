package com.robotemilio.glovotest.di.components

import android.app.Activity
import android.app.Application
import com.robotemilio.glovotest.GlovoTestApplication
import com.robotemilio.glovotest.di.ActivityBuilder
import com.robotemilio.glovotest.di.modules.GlovoTestModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    GlovoTestModule::class,
    ActivityBuilder::class
])
interface GlovoTestComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder
        fun build() : GlovoTestComponent
    }

    fun inject(application: GlovoTestApplication)

    override fun inject(instance: DaggerApplication?)
}