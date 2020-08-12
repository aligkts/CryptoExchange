package com.aligkts.cryptoexchange.model.service

import com.aligkts.cryptoexchange.model.dto.response.CoinResponseDTO
import com.aligkts.cryptoexchange.model.factory.RetrofitFactory
import retrofit2.http.GET

/**
 * Created by Ali Göktaş on 11.08.2020
 */
interface CoinService {
    companion object {
        val default: CoinService by lazy {
            RetrofitFactory.defaultRetrofit.create(CoinService::class.java)
        }
    }

    @GET("coin/list.php")
    suspend fun getCoins(): CoinResponseDTO
}
