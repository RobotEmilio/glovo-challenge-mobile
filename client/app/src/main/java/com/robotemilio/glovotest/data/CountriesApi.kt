package com.robotemilio.glovotest.data

import com.robotemilio.glovotest.data.cities.CityDTO
import com.robotemilio.glovotest.data.countries.CountryDTO
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {
    @GET("countries/")
    fun getCountryList() : Flowable<List<CountryDTO>>

    @GET("cities/")
    fun getCityList() : Flowable<List<CityDTO>>

    @GET("cities/{city}")
    fun getCityInfo(@Path("city") city : String) : Flowable<CityDTO>
}