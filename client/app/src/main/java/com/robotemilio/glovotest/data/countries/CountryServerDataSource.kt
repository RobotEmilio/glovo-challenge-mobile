package com.robotemilio.glovotest.data.countries

import com.robotemilio.glovotest.data.CountriesApi
import com.robotemilio.glovotest.data.common.ServerDataSource
import com.robotemilio.glovotest.domain.exception.CustomExceptionWrapper
import io.reactivex.Flowable
import javax.inject.Inject

class CountryServerDataSource @Inject constructor(private val countriesApi: CountriesApi) :
    ServerDataSource<CountryDTO> {

    override fun list(): Flowable<List<CountryDTO>> {
        return countriesApi.getCountryList()
            .onErrorResumeNext { t: Throwable ->
                Flowable.error(CustomExceptionWrapper(CustomExceptionWrapper.Code.NETWORK_ERROR, t))
            }
    }

}