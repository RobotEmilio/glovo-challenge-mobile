package com.robotemilio.glovotest.domain.usecase

import com.robotemilio.glovotest.data.countries.CountriesRepository
import com.robotemilio.glovotest.domain.model.Country
import io.reactivex.Flowable
import javax.inject.Inject

class GetCountries @Inject constructor(
    private val countriesRepository: CountriesRepository
) {

    fun getCountryList(): Flowable<List<Country>> {
        return countriesRepository.getCountries()
    }

}