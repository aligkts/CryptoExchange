package com.aligkts.cryptoexchange.model.repository

import com.aligkts.cryptoexchange.model.dto.response.CoinDetailDTO
import com.aligkts.cryptoexchange.model.dto.response.CoinGraphResponse
import com.aligkts.cryptoexchange.model.dto.response.CoinResponse
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
class DefaultCoinRepository(private val coinService: CoinService = CoinService.default) {

    private val compositeDisposable by lazy { CompositeDisposable() }
    val rxInterval by lazy { ObservableInterval.interval(0, 2, TimeUnit.SECONDS) }

    fun startPeriodicCoinRequest(onResult: (CoinResponse) -> Unit, onError: (Throwable) -> Unit) {
        compositeDisposable.add(rxInterval
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                coinService.getCoins()
                    .compose(applyIOAndUIScheduler())
                    .subscribe(object : ApiObserver<CoinResponse>(compositeDisposable) {
                        override fun onApiSuccess(data: CoinResponse) {
                            onResult(data)
                        }

                        override fun onApiError(er: Throwable) {
                            onError(er)
                        }
                    })
            })
    }

    fun startPeriodicCoinDetailRequest(
        code: String,
        onResult: (CoinDetailDTO) -> Unit,
        onError: (Throwable) -> Unit
    ) {
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

    fun requestCoinGraph(
        code: String,
        onResult: (CoinGraphResponse) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        compositeDisposable.add(rxInterval
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                coinService.getCoinGraph(code)
                    .compose(applyIOAndUIScheduler())
                    .subscribe(object : ApiObserver<CoinGraphResponse>(compositeDisposable) {
                        override fun onApiSuccess(data: CoinGraphResponse) {
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

}