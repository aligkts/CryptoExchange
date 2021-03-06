package com.aligkts.cryptoexchange.model.service

import com.aligkts.cryptoexchange.model.dto.response.CoinDetailDTO
import com.aligkts.cryptoexchange.model.dto.response.CoinGraphResponse
import com.aligkts.cryptoexchange.model.dto.response.CoinResponse
import com.aligkts.cryptoexchange.model.factory.RetrofitFactory
import io.reactivex.Observable
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
    fun getCoins(): Observable<CoinResponse>

    @GET("coin/detail.php")
    fun getCoinDetails(@Query("cod") cod: String): Observable<CoinDetailDTO>

    @GET("coin/graph.php")
    fun getCoinGraph(@Query("cod") cod: String): Observable<CoinGraphResponse>
}
