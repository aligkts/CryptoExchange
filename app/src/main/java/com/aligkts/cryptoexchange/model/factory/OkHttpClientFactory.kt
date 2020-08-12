package com.aligkts.cryptoexchange.model.factory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Created by Ali Göktaş on 11.08.2020
 */
class OkHttpClientFactory {
    companion object {
        val defaultClient: OkHttpClient by lazy {
            OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        }
    }
}