package com.robotemilio.glovotest.ui.countrylist

import androidx.lifecycle.MutableLiveData
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import com.robotemilio.glovotest.domain.usecase.GetCountries
import com.robotemilio.glovotest.ui.common.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CountriesViewModel @Inject constructor(
    private val getCountries: GetCountries
) : BaseViewModel() {

    val countries = MutableLiveData<List<CountryUiItem>>()

    fun getCountries() {
        Flowable.zip(
            getCountries.getCountryList(),
            getCountries.getCityList(),
            BiFunction { countries: List<Country>, cities: List<City> ->
                cities.groupBy { it.countryCode }.mapNotNull { entry ->
                    val country = countries.find { it.code == entry.key }
                    country?.let { CountryUiItem(it, entry.value) }
                }.sortedBy { it.country.name }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = {
                countries.value = it
            }, onError = {
                errorsReceived.value = it
            })
            .also(::addDisposable)
    }

    data class CountryUiItem(val country: Country, val cities: List<City>)

}