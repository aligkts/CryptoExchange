package com.aligkts.cryptoexchange.ui.detail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.model.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val coinDetail = MutableLiveData<List<DItem>>()

    private val coinRepository: CoinRepository = CoinRepository.default

    fun getCoinDetails(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.getCoinDetails(code, completion = {
                withContext(Dispatchers.Main) {
                    this@DetailViewModel.coinDetail.value = it.items
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