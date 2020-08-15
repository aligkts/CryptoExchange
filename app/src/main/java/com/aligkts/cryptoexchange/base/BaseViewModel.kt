package com.aligkts.cryptoexchange.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.observable.ObservableInterval
import java.util.concurrent.TimeUnit

/**
 * Created by Ali Göktaş on 11.08.2020
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    val contentLoading = MutableLiveData<Boolean>()
    lateinit var errorHandler: ErrorHandler

    val compositeDisposable by lazy {  CompositeDisposable() }
    val rxInterval by lazy { ObservableInterval.interval(0, 2, TimeUnit.SECONDS) }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}