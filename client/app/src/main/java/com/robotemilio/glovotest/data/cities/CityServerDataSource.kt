package com.robotemilio.glovotest.data.cities

import com.robotemilio.glovotest.data.CountriesApi
import com.robotemilio.glovotest.data.common.ServerDataSource
import com.robotemilio.glovotest.domain.exception.CustomExceptionWrapper
import io.reactivex.Flowable
import javax.inject.Inject

class CityServerDataSource @Inject constructor(private val countriesApi: CountriesApi) :
    ServerDataSource<CityDTO> {

    override fun list(): Flowable<List<CityDTO>> {
        return countriesApi.getCityList()
            .onErrorResumeNext { t: Throwable ->
                Flowable.error(CustomExceptionWrapper(CustomExceptionWrapper.Code.NETWORK_ERROR, t))
            }
    }

    fun get(code: String): Flowable<CityDTO> {
        return countriesApi.getCityInfo(code)
            .onErrorResumeNext { t: Throwable ->
                Flowable.error(CustomExceptionWrapper(CustomExceptionWrapper.Code.NETWORK_ERROR, t))
            }
    }
}