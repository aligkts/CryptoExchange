package com.aligkts.cryptoexchange.util

import com.aligkts.cryptoexchange.extension.getDateTime
import com.github.mikephil.charting.formatter.ValueFormatter

/**
 * Created by Ali Göktaş on 16,August,2020
 */

class DateAxisValueFormatter : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        return value.toLong().getDateTime()
    }

}

