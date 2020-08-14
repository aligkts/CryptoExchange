package com.aligkts.cryptoexchange.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinDetailDTO
import com.aligkts.cryptoexchange.model.dto.response.DItem
import com.aligkts.cryptoexchange.model.repository.CoinRepository
import com.aligkts.cryptoexchange.model.service.CoinService
import com.aligkts.cryptoexchange.ui.home.RxUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val coinDetail = MutableLiveData<List<DItem>>()

    private val coinRepository: CoinRepository = CoinRepository.default

    private var coinDetailDisposable: Disposable? = null

    fun startPeriodicCoinDetailRequests(code: String) {
        coinDetailDisposable =
            Observable.interval(2, 2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { timer: Long? -> requestCoinDetails(code) }
                ) { throwable: Throwable ->
                    Log.d("DetailViewModel", "Periodic Coin Detail Request Timer Failed : $throwable")
                }
    }

    private fun requestCoinDetails(code: String) {
        compositeDisposable.add(
            CoinService.default.getCoinDetails(code)
            .compose(RxUtil.applyIOAndUISchedulerToSingle())
            .subscribe(
                { response: CoinDetailDTO? ->
                    this@DetailViewModel.coinDetail.value = response?.items
                }
            ) { throwable: Throwable ->
                Log.d("DetailViewModel", "Periodic Coin Detail Request Failed : $throwable")
            })
    }

    fun stopPeriodicCoinDetailRequests() {
        coinDetailDisposable?.dispose()
    }



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