package com.robotemilio.glovotest.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    /**
     * It will contain all usecase disposables
     */
    private val disposables = CompositeDisposable()


    /**
     * It will contain the errors that UI must receive
     */
    val errorsReceived = MutableLiveData<Throwable>()

    /**
     * Add a usecase execution to the pool of disposables
     */
    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    /**
     * Whenever viewmodel is finish, dispose all disposables
     */
    override fun onCleared() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
        super.onCleared()
    }

}