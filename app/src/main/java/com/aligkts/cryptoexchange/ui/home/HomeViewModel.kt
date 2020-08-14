package com.aligkts.cryptoexchange.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.aligkts.cryptoexchange.base.BaseViewModel
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.dto.response.CoinResponseDTO
import com.aligkts.cryptoexchange.model.repository.CoinRepository
import com.aligkts.cryptoexchange.model.repository.GenericSecureRepository
import com.aligkts.cryptoexchange.model.service.CoinService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class HomeViewModel(application: Application) : BaseViewModel(application) {

    val coins = MutableLiveData<ArrayList<CoinItemDTO>>()

    private val coinRepository: CoinRepository = CoinRepository.default
    val genericSecureRepository: GenericSecureRepository = GenericSecureRepository.default

    private var coinValuesDisposable: Disposable? = null

    fun startPeriodicCoinRequests() {
        coinValuesDisposable =
            Observable.interval(2, 2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { timer: Long? -> requestCoins() }
                ) { throwable: Throwable ->
                    Log.d("HomeViewModel", "Periodic Coin Request Timer Failed : $throwable")
                }
    }

    fun stopPeriodicCoinRequests() {
        coinValuesDisposable?.dispose()
    }

    private fun requestCoins() {
        compositeDisposable.add(CoinService.default.getCoins()
            .compose(RxUtil.applyIOAndUISchedulerToSingle())
            .subscribe(
                { response: CoinResponseDTO? ->
                    this@HomeViewModel.coins.value = response?.coins
                }
            ) { throwable: Throwable ->
                Log.d("HomeViewModel", "Periodic Coin Request Failed : $throwable")
            })
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

    fun getCoins() {

        /*viewModelScope.launch(Dispatchers.IO) {
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
        }*/
    }
}