package com.aligkts.cryptoexchange.ui.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinGraphResponse
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.model.repository.DefaultCoinRepository

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val coinDetail = MutableLiveData<List<DItem>>()
    val coinGraphData = MutableLiveData<CoinGraphResponse>()

    private val coinRepository by lazy { DefaultCoinRepository() }

    fun startPeriodicCoinDetailRequests(code: String) {
        contentLoading.value = true
        coinRepository.startPeriodicCoinDetailRequest(code, {
            contentLoading.value = false
            this@DetailViewModel.coinDetail.value = it.items
        }, { throwable ->
            contentLoading.value = false
            stopPeriodicRequest()
            errorHandler.handleError(
                throwable.message
                    ?: getApplication<Application>().getString(R.string.error_general_message)
            )
        })
    }

    fun getCoinGraphData(code: String) {
        contentLoading.value = true
        coinRepository.requestCoinGraph(code, {
            contentLoading.value = false
            this@DetailViewModel.coinGraphData.value = it
        }, { throwable ->
            contentLoading.value = false

            errorHandler.handleError(
                throwable.message
                    ?: getApplication<Application>().getString(R.string.error_general_message)
            )
        })
    }

    fun stopPeriodicRequest() {
        coinRepository.clearDisposable()
    }

}