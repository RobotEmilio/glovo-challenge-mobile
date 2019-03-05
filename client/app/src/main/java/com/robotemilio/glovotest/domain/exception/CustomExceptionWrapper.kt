package com.robotemilio.glovotest.domain.exception

data class CustomExceptionWrapper(
    val code : Code = Code.UNKNOWN,
    val throwable: Throwable
) : Throwable() {

    enum class Code(val code : Int) {
        NETWORK_ERROR(1),
        UNKNOWN(999)
    }

}