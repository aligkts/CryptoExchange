package com.aligkts.cryptoexchange.model.service

import com.aligkts.cryptoexchange.model.dto.response.CoinDetailDTO
import com.aligkts.cryptoexchange.model.dto.response.CoinResponseDTO
import com.aligkts.cryptoexchange.model.factory.RetrofitFactory
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

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
    fun getCoinsOld(): CoinResponseDTO

    @GET("coin/detail.php")
    fun getCoinDetailsOld(@Query("cod") cod: String): CoinDetailDTO

    @GET("coin/list.php")
    fun getCoins(): Single<CoinResponseDTO>

    @GET("coin/detail.php")
    fun getCoinDetails(@Query("cod") cod: String): Single<CoinDetailDTO>
}