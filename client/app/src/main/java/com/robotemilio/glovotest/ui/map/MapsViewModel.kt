package com.robotemilio.glovotest.ui.map

import androidx.lifecycle.MutableLiveData
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import com.robotemilio.glovotest.domain.usecase.GetCountries
import com.robotemilio.glovotest.ui.common.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val getCountries: GetCountries
) : BaseViewModel() {

    val countriesLiveData = MutableLiveData<List<Country>>()
    val citiesLiveData = MutableLiveData<List<City>>()
    val currentCityLiveData = MutableLiveData<City>()

    fun getCountryList() {
        getCountries.getCountryList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { error ->
                    errorsReceived.value = error
                },
                onNext = { countries ->
                    countriesLiveData.value = countries
                })
            .also(::addDisposable)
    }

    fun getCityList() {
        getCountries.getCityList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { error ->
                    errorsReceived.value = error
                },
                onNext = { cities ->
                    citiesLiveData.value = cities
                }
            )
            .also(::addDisposable)
    }

    fun getCityInfo(city: City) {
            getCountries.getCityInfo(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = { error ->
                    errorsReceived.value = error
                },
                onNext = { city ->
                    currentCityLiveData.value = city
                }
            )
            .also(::addDisposable)
    }

}