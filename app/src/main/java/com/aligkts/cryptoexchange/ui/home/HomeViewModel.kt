package com.aligkts.cryptoexchange.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val coins = MutableLiveData<ArrayList<CoinItemDTO>>()

    private val coinRepository: CoinRepository = CoinRepository.default

    fun getCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.getCoins(completion = {
                withContext(Dispatchers.Main) {
                    this@HomeViewModel.coins.value = it
                }
            }, error = { errorResponseDTO ->
                    withContext(Dispatchers.Main) {
                        errorHandler?.handleError(
                            errorResponseDTO?.errorMessageForUser
                                ?: getApplication<Application>().getString(
                                    R.string.error_general_message
                                )
                        )
                    }
                })
        }
    }
}