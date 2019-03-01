package com.robotemilio.glovotest.data.cities

import com.robotemilio.glovotest.data.CountriesApi
import com.robotemilio.glovotest.data.common.ServerDataSource
import com.robotemilio.glovotest.domain.exception.CustomException
import com.robotemilio.glovotest.domain.exception.CustomException.Layer.*
import io.reactivex.Flowable
import javax.inject.Inject

class CityServerDataSource @Inject constructor(private val countriesApi: CountriesApi) :
        ServerDataSource<CityDTO> {

    override fun list(): Flowable<List<CityDTO>> {
        return countriesApi.getCityList()
    }

    fun get(code : String): Flowable<CityDTO> {
        return countriesApi.getCityInfo(code)
            .onErrorResumeNext { t: Throwable ->
                Flowable.error(CustomException(CustomException.Layer.DATA_SOURCE, CustomException.Code.NETWORK_ERROR))
            }
    }
}