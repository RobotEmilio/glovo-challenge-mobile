package com.robotemilio.glovotest.extensions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.robotemilio.glovotest.di.DaggerViewModelFactory

inline fun <reified T : ViewModel> getViewModel(
    activity: AppCompatActivity,
    factory: DaggerViewModelFactory
): T {
    return ViewModelProviders.of(activity, factory)[T::class.java]
}
fun <T : Any?, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

fun Any.logError(throwable: Throwable, printStackTrace: Boolean = false) {
    Log.e("GlovoTestAntonio", throwable.localizedMessage)
    if (printStackTrace) {
        throwable.printStackTrace()
    }
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}