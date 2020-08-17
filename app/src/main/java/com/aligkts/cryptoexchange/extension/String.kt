package com.aligkts.cryptoexchange.extension

import com.aligkts.cryptoexchange.MainApplication.Companion.appContext
import com.aligkts.cryptoexchange.R

/**
 * Created by Ali Göktaş on 12,August,2020
 */

fun String.getCoinSpinnerSelectedIndex(): Int {
    val list = appContext.resources.getStringArray(R.array.CoinSpinnerItems)
    for ((index, value) in list.withIndex()) {
        if (value == this) {
            return index
        }
    }
    return 0
}