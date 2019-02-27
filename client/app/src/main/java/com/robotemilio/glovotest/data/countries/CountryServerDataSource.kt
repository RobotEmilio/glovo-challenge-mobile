package com.robotemilio.glovotest.data.countries

import com.robotemilio.glovotest.data.CountriesApi
import com.robotemilio.glovotest.data.common.ServerDataSource
import io.reactivex.Flowable
import javax.inject.Inject

class CountryServerDataSource @Inject constructor(private val countriesApi: CountriesApi) :
    ServerDataSource<Flowable<List<CountryDTO>>> {

    override fun retrieve(): Flowable<List<CountryDTO>> {
        return countriesApi.getCountryList()
    }

}