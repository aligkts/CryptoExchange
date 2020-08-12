package com.aligkts.cryptoexchange.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by Ali Göktaş on 11.08.2020
 */
fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}