package com.robotemilio.glovotest.di.modules

import androidx.annotation.NonNull
import com.robotemilio.glovotest.BuildConfig
import com.robotemilio.glovotest.data.CountriesApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetModule {

    companion object {
        const val SERVER_BASE_URL = "http://192.168.0.20:3000/api/"
    }

    @Singleton
    @Provides
    fun providesRetrofit(loggingInterceptor: Interceptor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(SERVER_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesInterceptor() : Interceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Singleton
    @Provides
    fun provideCitiesApi(@NonNull retrofit: Retrofit) : CountriesApi {
        return retrofit.create(CountriesApi::class.java)
    }

}