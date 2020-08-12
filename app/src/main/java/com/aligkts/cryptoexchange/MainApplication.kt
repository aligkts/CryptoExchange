package com.aligkts.cryptoexchange

import android.app.Application

/**
 * Created by Ali Göktaş on 11.08.2020
 */
class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }
}