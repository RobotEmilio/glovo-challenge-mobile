package com.robotemilio.glovotest.domain.model

data class City(
    val countryCode: String,
    val name : String,
    var currency: String? = null,
    val code: String,
    var enabled: Boolean? = null,
    var timeZone: String? = null,
    val workingArea: List<AreaShape>,
    var languageCode: String? = null,
    var busy: Boolean? = null
)