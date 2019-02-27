package com.robotemilio.glovotest.data.common

import io.reactivex.Flowable

interface ServerDataSource<T> {
    fun list() : Flowable<List<T>>
}