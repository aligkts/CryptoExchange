package com.aligkts.cryptoexchange.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.repository.DefaultCoinRepository
import com.aligkts.cryptoexchange.model.repository.GenericSecureRepository

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val coins = MutableLiveData<ArrayList<CoinItemDTO>>()

    private val coinRepository by lazy { DefaultCoinRepository() }
    val genericSecureRepository by lazy { GenericSecureRepository.default }

    fun startPeriodicCoinRequests() {
        contentLoading.value = true
        coinRepository.startPeriodicCoinRequest({
            contentLoading.value = false
            this@HomeViewModel.coins.value = it.coins
        },{ throwable ->
            stopPeriodicCoinRequests()
            contentLoading.value = false
            errorHandler.handleError(throwable.message
                ?: getApplication<Application>().getString(R.string.error_general_message))
        })
    }

    fun stopPeriodicCoinRequests() {
        coinRepository.clearDisposable()
    }

}