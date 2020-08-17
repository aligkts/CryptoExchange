package com.aligkts.cryptoexchange

import android.app.Application
import android.content.Context

/**
 * Created by Ali Göktaş on 11.08.2020
 */
class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
        lateinit  var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
    }
}