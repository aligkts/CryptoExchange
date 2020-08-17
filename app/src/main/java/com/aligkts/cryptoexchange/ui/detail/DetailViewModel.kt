package com.aligkts.cryptoexchange.ui.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinGraphResponse
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.model.repository.DefaultCoinRepository
import com.aligkts.cryptoexchange.model.repository.GenericSecureRepository
import com.aligkts.cryptoexchange.util.Constant

/**
 * Created by Ali Göktaş on 14,August,2020
 */
class DetailViewModel(application: Application) : BaseViewModel(application) {

    val coinDetail = MutableLiveData<List<DItem>>()
    val coinGraphData = MutableLiveData<CoinGraphResponse>()

    private val coinRepository by lazy { DefaultCoinRepository() }
    val genericSecureRepository by lazy { GenericSecureRepository.default }

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
            errorHandler.handleError(throwable.message ?: getApplication<Application>().getString(R.string.error_general_message))
        })
    }

    fun stopPeriodicRequest() {
        coinRepository.clearDisposable()
    }

    fun addFavoriteCoin(id: String) {
        val favoriteList = getFavoriteListFromPrefs()
        favoriteList.add(id)
        genericSecureRepository.putStringList(Constant.FAVORITES_KEY, favoriteList)
    }

    fun deleteFavoriteCoin(id: String) {
        val favoriteList = getFavoriteListFromPrefs()
        favoriteList.remove(id)
        genericSecureRepository.putStringList(Constant.FAVORITES_KEY, favoriteList)
    }

    private fun getFavoriteListFromPrefs(): ArrayList<String> = genericSecureRepository.getStringList(Constant.FAVORITES_KEY)


}