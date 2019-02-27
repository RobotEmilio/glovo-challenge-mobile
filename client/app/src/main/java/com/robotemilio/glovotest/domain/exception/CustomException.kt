package com.robotemilio.glovotest.domain.exception

data class CustomException(
    val layer : Layer,
    val classError : String
) : Throwable() {

    enum class Layer {
        DATA_SOURCE, REPOSITORY, USE_CASE
    }

}