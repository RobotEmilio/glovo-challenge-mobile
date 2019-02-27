package com.robotemilio.glovotest.domain.usecase

import com.robotemilio.glovotest.data.CountriesRepository
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import io.reactivex.Flowable
import javax.inject.Inject

class GetCountries @Inject constructor(
    private val countriesRepository: CountriesRepository
) {

    fun getCountryList(): Flowable<List<Country>> {
        return countriesRepository.getCountries()
    }

    fun getCityList(): Flowable<List<City>> {
        return countriesRepository.getCities()
    }

    fun getCityInfo(city: City): Flowable<City> {
        return countriesRepository.getCityInfo(city)
    }

}