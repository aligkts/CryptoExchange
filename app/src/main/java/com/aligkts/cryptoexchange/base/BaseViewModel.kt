package com.aligkts.cryptoexchange.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Ali Göktaş on 11.08.2020
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

    val contentLoading = MutableLiveData<Boolean>()
    var errorHandler: ErrorHandler? = null

    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}