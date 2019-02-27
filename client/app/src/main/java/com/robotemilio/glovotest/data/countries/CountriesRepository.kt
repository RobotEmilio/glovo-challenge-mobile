package com.robotemilio.glovotest.data.countries

import com.robotemilio.glovotest.domain.model.Country
import io.reactivex.Flowable
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val serverSource: CountryServerDataSource,
    private val countriesMapper: CountriesMapper
) {

    fun getCountries(): Flowable<List<Country>> {
        return serverSource
            .retrieve()
            .map { countriesDto ->
                countriesDto.map { countryDTO ->
                    countriesMapper.dtoToModel(countryDTO)
                }
            }
    }

}