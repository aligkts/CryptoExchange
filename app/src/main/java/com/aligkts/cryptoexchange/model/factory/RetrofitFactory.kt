package com.aligkts.cryptoexchange.model.factory

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Ali Göktaş on 11.08.2020
 */
class RetrofitFactory {
    companion object {
        val defaultRetrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl("http://www.aslinda.net/")
                .client(OkHttpClientFactory.defaultClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}