package com.robotemilio.glovotest.data.cities

import com.google.maps.android.PolyUtil
import com.robotemilio.glovotest.domain.model.AreaShape
import com.robotemilio.glovotest.domain.model.City
import com.robotemilio.glovotest.domain.model.Country
import javax.inject.Inject

class CityMapper @Inject constructor() {

    fun dtoToModel(cityDTO: CityDTO): City {
        return City(
            countryCode = cityDTO.countryCode,
            name = cityDTO.name,
            code = cityDTO.code,
            workingArea = cityDTO.workingArea.map { AreaShape(PolyUtil.decode(it)) }
        )
    }

}