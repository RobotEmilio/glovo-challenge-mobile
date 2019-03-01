package com.robotemilio.glovotest.domain.exception

data class CustomException(
    val layer : Layer,
    val code : Code = Code.UNKNOWN
) : Throwable() {

    enum class Layer {
        DATA_SOURCE, REPOSITORY, USE_CASE
    }

    enum class Code(val code : Int) {
        NETWORK_ERROR(1),
        UNKNOWN(999)
    }

}