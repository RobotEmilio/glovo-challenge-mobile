package com.robotemilio.glovotest.data

import com.robotemilio.glovotest.data.countries.CountryDTO
import io.reactivex.Flowable
import retrofit2.http.GET

interface CountriesApi {
    @GET("countries/")
    fun getCountryList() : Flowable<List<CountryDTO>>
}