package com.robotemilio.glovotest.ui.map

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.robotemilio.glovotest.domain.model.Country
import com.robotemilio.glovotest.domain.usecase.GetCountries
import com.robotemilio.glovotest.ui.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapsActivityViewModel @Inject constructor(
    private val getCountries: GetCountries
) : BaseViewModel() {

    val countriesLiveData = MutableLiveData<List<Country>>()

    fun testInjection() {
        Log.d("HOLA", "MUNDO")
    }

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

}