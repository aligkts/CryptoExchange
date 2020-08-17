package com.aligkts.cryptoexchange.extension

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ali Göktaş on 16,August,2020
 */
fun Long.getDateTime(): String {
    return try {
        val sdf = SimpleDateFormat("MM.dd.yyyy")
        val netDate = Date(this)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}