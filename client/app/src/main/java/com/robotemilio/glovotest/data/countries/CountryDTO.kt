package com.robotemilio.glovotest.data.countries

import com.google.gson.annotations.SerializedName

data class CountryDTO(
    @SerializedName("code") val code : String,
    @SerializedName("name") val name : String
)