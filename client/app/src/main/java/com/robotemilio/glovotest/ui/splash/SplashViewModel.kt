package com.robotemilio.glovotest.ui.splash

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.usecase.GetCountries
import com.robotemilio.glovotest.ui.common.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashViewModel @Inject constructor(val getCountries: GetCountries) : BaseViewModel() {

    val cityInRange = MutableLiveData<City?>()

    fun checkInRange(lastLocation: LatLng) {
        getCountries.getCityList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = { cities ->
                cityInRange.value = cities.find { city ->
                    city.workingArea.any {
                        PolyUtil.containsLocation(lastLocation, it.points, true)
                    }
                }
            }, onError = {
                errorsReceived.value = it
            }).also(::addDisposable)
    }

}