package com.robotemilio.glovotest.data.countries

import com.robotemilio.glovotest.domain.model.Country
import javax.inject.Inject

class CountriesMapper @Inject constructor() {

    fun dtoToModel(countryDto: CountryDTO): Country {
        return Country(code = countryDto.code, name = countryDto.name)
    }

}