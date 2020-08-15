package com.aligkts.cryptoexchange.model.repository

import com.aligkts.cryptoexchange.model.dto.response.CoinDetailDTO
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.dto.response.CoinResponseDTO
import com.aligkts.cryptoexchange.model.service.ApiObserver
import com.aligkts.cryptoexchange.model.service.CoinService
import com.aligkts.cryptoexchange.util.RxUtil.applyIOAndUIScheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.observable.ObservableInterval
import java.util.concurrent.TimeUnit

/**
 * Created by Ali Göktaş on 11.08.2020
 */
interface CoinRepository {
    companion object {
        val default: CoinRepository by lazy { DefaultCoinRepository() }
    }
}

class DefaultCoinRepository(val coinService: CoinService = CoinService.default) :
    CoinRepository {
    lateinit var coins: List<CoinItemDTO>
    lateinit var coinDetail: CoinDetailDTO

    private val compositeDisposable by lazy { CompositeDisposable() }
    val rxInterval by lazy { ObservableInterval.interval(0, 2, TimeUnit.SECONDS) }

    fun startPeriodicCoinRequest(onResult: (CoinResponseDTO) -> Unit, onError: (Throwable) -> Unit) {
        compositeDisposable.add(rxInterval
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                coinService.getCoins()
                    .compose(applyIOAndUIScheduler())
                    .subscribe(object : ApiObserver<CoinResponseDTO>(compositeDisposable) {
                        override fun onApiSuccess(data: CoinResponseDTO) {
                            onResult(data)
                        }

                        override fun onApiError(er: Throwable) {
                            onError(er)
                        }
                    })
            })
    }

    fun startPeriodicCoinDetailRequest(code: String,
                                       onResult: (CoinDetailDTO) -> Unit,
                                       onError: (Throwable) -> Unit) {
        compositeDisposable.add(rxInterval
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                coinService.getCoinDetails(code)
                    .compose(applyIOAndUIScheduler())
                    .subscribe(object : ApiObserver<CoinDetailDTO>(compositeDisposable) {
                        override fun onApiSuccess(data: CoinDetailDTO) {
                            onResult(data)
                        }

                        override fun onApiError(er: Throwable) {
                            onError(er)
                        }
                    })
            })
    }

    fun clearDisposable() {
        compositeDisposable.clear()
    }

    /*override suspend fun getCoins(
        completion: suspend (ArrayList<CoinItemDTO>) -> Unit,
        error: suspend (ErrorResponseDTO?) -> Unit
    ) {

        when(val serviceResult = makeServiceRequest { coinService.getCoinsOld() }) {
            is ServiceResult.Success -> {
                coins = serviceResult.body.coins
                completion(serviceResult.body.coins)
            }
            is ServiceResult.Error -> error(serviceResult.errorDTO)
            is ServiceResult.UnknownError -> error(null)
        }
    }*/

}