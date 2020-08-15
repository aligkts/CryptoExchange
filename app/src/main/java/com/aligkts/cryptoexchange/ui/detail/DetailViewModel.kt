package com.aligkts.cryptoexchange.ui.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinDetailDTO
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.model.repository.DefaultCoinRepository
import com.aligkts.cryptoexchange.model.service.CoinService
import com.aligkts.cryptoexchange.util.RxUtil
import io.reactivex.disposables.Disposable

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val coinDetail = MutableLiveData<List<DItem>>()

    private val coinRepository by lazy { DefaultCoinRepository() }

    private lateinit var coinDetailDisposable: Disposable

    fun startPeriodicCoinDetailRequests(code: String) {
        /*coinDetailDisposable = rxInterval
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ requestCoinDetails(code) }) { throwable: Throwable ->
                    errorHandler.handleError(throwable.message
                        ?: getApplication<Application>().getString(R.string.error_general_message))
                }*/
        coinRepository.startPeriodicCoinDetailRequest(code, {
            this@DetailViewModel.coinDetail.value = it.items
        }, { throwable ->
            stopPeriodicCoinDetailRequests()
            errorHandler.handleError(
                throwable.message
                    ?: getApplication<Application>().getString(R.string.error_general_message)
            )
        })
    }

    fun stopPeriodicCoinDetailRequests() {
        coinRepository.clearDisposable()
    }

    private fun requestCoinDetails(code: String) {
        compositeDisposable.add(
            CoinService.default.getCoinDetails(code)
                .compose(RxUtil.applyIOAndUIScheduler())
                .subscribe({ response: CoinDetailDTO? ->
                    this@DetailViewModel.coinDetail.value = response?.items
                }) { throwable: Throwable ->
                    stopPeriodicCoinDetailRequests()
                    errorHandler.handleError(
                        throwable.message
                            ?: getApplication<Application>().getString(R.string.error_general_message)
                    )
                })
    }

}