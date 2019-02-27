package com.robotemilio.glovotest.data.common

interface ServerDataSource<T> {
    fun retrieve() : T
}