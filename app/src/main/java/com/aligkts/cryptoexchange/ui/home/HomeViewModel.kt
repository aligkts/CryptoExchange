package com.aligkts.cryptoexchange.ui.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.aligkts.cryptoexchange.R
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.repository.DefaultCoinRepository
import com.aligkts.cryptoexchange.model.repository.GenericSecureRepository
import io.reactivex.disposables.Disposable

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val coins = MutableLiveData<ArrayList<CoinItemDTO>>()

    private val coinRepository by lazy { DefaultCoinRepository() }
    val genericSecureRepository by lazy { GenericSecureRepository.default }

    private lateinit var coinValuesDisposable: Disposable

    fun startPeriodicCoinRequests() {
        /*coinValuesDisposable = rxInterval
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ requestCoins() }) { throwable: Throwable ->
                    errorHandler.handleError(throwable.message
                        ?: getApplication<Application>().getString(R.string.error_general_message))
                }*/
        coinRepository.startPeriodicCoinRequest({
            this@HomeViewModel.coins.value = it.coins
        },{ throwable ->
            stopPeriodicCoinRequests()
            errorHandler.handleError(throwable.message
                ?: getApplication<Application>().getString(R.string.error_general_message))
        })
    }

    fun stopPeriodicCoinRequests() {
        coinRepository.clearDisposable()
    }

    private fun requestCoins() {
        /*compositeDisposable.add(CoinService.default.getCoins()
            .compose(RxUtil.applyIOAndUIScheduler())
            .subscribe({ response: CoinResponseDTO? ->
                    this@HomeViewModel.coins.value = response?.coins
            }) { throwable: Throwable ->
                stopPeriodicCoinRequests()
                errorHandler.handleError(throwable.message
                    ?: getApplication<Application>().getString(R.string.error_general_message))
            })*/
        /*MainApplication.serviceAdapterFactory().claimsServiceAdapter().getClaimsSummary(
            object : RequestListener<java.util.ArrayList<ClaimsGetSummaryResponseItem?>?>() {
                fun onLoad(result: java.util.ArrayList<ClaimsGetSummaryResponseItem>) {
                    Log.d(MainActivity.TAG, "ClaimsSummaryResponse: $result")
                    var hasOngoingProcess = false
                    if (result.size > 0) {
                        changeDamagedAssetIconVisibility(true)
                        for (item in result) {
                            if (item.getClaimsTowInfo() != null || item.getClaimsMotorInfo() != null || item.getClaimsRentalInfo() != null) {
                                hasOngoingProcess = true
                                break
                            }
                        }
                        handleSummaryResponse(result)
                    } else {
                        changeDamagedAssetIconVisibility(false)
                    }
                    MainApplication.setUserHasOngoingClaimsProcessLiveData(hasOngoingProcess)
                }

                fun onFail(exception: OneAppException?) {
                    endPeriodicSummaryRequests()
                }
            }
        )*/
    }
}