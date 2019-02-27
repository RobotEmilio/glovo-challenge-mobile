package com.robotemilio.glovotest.data

import com.robotemilio.glovotest.data.cities.CityMapper
import com.robotemilio.glovotest.data.cities.CityServerDataSource
import com.robotemilio.glovotest.data.countries.CountriesMapper
import com.robotemilio.glovotest.data.countries.CountryServerDataSource
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import io.reactivex.Flowable
import javax.inject.Inject

class CountriesRepository @Inject constructor(
    private val countries: CountryServerDataSource,
    private val cities: CityServerDataSource,
    private val countriesMapper: CountriesMapper,
    private val citiesMapper: CityMapper
) {

    fun getCountries(): Flowable<List<Country>> {
        return countries
            .list()
            .map { countriesDto ->
                countriesDto.map { countryDTO ->
                    countriesMapper.dtoToModel(countryDTO)
                }
            }
    }

    fun getCities(): Flowable<List<City>> {
        return cities
            .list()
            .map { citiesDto ->
                citiesDto.map { cityDTO ->
                    citiesMapper.dtoToModel(cityDTO)
                }
            }
    }

    fun getCityInfo(city : City): Flowable<City> {
        return cities
            .get(city.code)
            .map { cityDTO ->
                city.apply {
                    busy = cityDTO.busy
                    currency = cityDTO.currency
                    enabled = cityDTO.enabled
                    timeZone = cityDTO.timeZone
                    languageCode = cityDTO.languageCode
                }
            }
    }

}