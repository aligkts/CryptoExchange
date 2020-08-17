package com.aligkts.cryptoexchange.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.repository.DefaultCoinRepository
import com.aligkts.cryptoexchange.model.repository.GenericSecureRepository
import com.aligkts.cryptoexchange.util.Constant

/**
 * Created by Ali Göktaş on 11,August,2020
 */
class HomeViewModel(application: Application) : BaseViewModel(application) {

    val coins = MutableLiveData<ArrayList<CoinItemDTO>>()

    var filterFavorites: Boolean = false
    private val coinRepository by lazy { DefaultCoinRepository() }
    val genericSecureRepository by lazy { GenericSecureRepository.default }

    fun startPeriodicCoinRequests() {
        contentLoading.value = true
        coinRepository.startPeriodicCoinRequest({ coinResponse ->
            contentLoading.value = false
            if (filterFavorites) {
                this@HomeViewModel.coins.value = getFilteredCoins(coinResponse.coins)
            } else {
                this@HomeViewModel.coins.value = coinResponse.coins
            }
        }, { throwable ->
            stopPeriodicCoinRequests()
            contentLoading.value = false
            errorHandler.handleError(throwable.message
                ?: getApplication<Application>().getString(R.string.error_general_message))
        })
    }

    private fun getFilteredCoins(coins: ArrayList<CoinItemDTO>): ArrayList<CoinItemDTO> {
        val filteredCoins = ArrayList<CoinItemDTO>()
        val favoriteCoinCodeList = genericSecureRepository.getStringList(Constant.FAVORITES_KEY)
        for (coinCode in favoriteCoinCodeList) {
            val foundCoin: CoinItemDTO? = coins.first { it.id == coinCode }
            foundCoin?.let { filteredCoins.add(it) }
        }
        return filteredCoins
    }

    fun checkWhetherClickedCoinFavorited(id: String): Boolean {
        return genericSecureRepository.getStringList(Constant.FAVORITES_KEY).contains(id)
    }

    fun stopPeriodicCoinRequests() {
        coinRepository.clearDisposable()
    }

}