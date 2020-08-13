package com.aligkts.cryptoexchange.model.repository

import com.aligkts.cryptoexchange.model.dto.response.CoinDetailDTO
import com.aligkts.cryptoexchange.model.dto.response.CoinItemDTO
import com.aligkts.cryptoexchange.model.dto.response.ErrorResponseDTO
import com.aligkts.cryptoexchange.model.service.CoinService
import com.aligkts.cryptoexchange.model.service.ServiceResult
import com.aligkts.cryptoexchange.model.service.makeServiceRequest

/**
 * Created by Ali Göktaş on 11.08.2020
 */
interface CoinRepository {
    companion object {
        val default: CoinRepository by lazy { DefaultCoinRepository() }
    }

    suspend fun getCoins(
        completion: suspend (ArrayList<CoinItemDTO>) -> Unit,
        error: suspend (ErrorResponseDTO?) -> Unit
    )

    suspend fun getCoinDetails(
        cod: String,
        completion: suspend (CoinDetailDTO) -> Unit,
        error: suspend (ErrorResponseDTO?) -> Unit
    )
}

class DefaultCoinRepository(val coinService: CoinService = CoinService.default) :
    CoinRepository {
    private var coins: List<CoinItemDTO>?= null
    private var coinDetail: CoinDetailDTO?= null

    override suspend fun getCoins(
        completion: suspend (ArrayList<CoinItemDTO>) -> Unit,
        error: suspend (ErrorResponseDTO?) -> Unit
    ) {

        when(val serviceResult = makeServiceRequest { coinService.getCoins() }) {
            is ServiceResult.Success -> {
                coins = serviceResult.body.coins
                completion(serviceResult.body.coins)
            }
            is ServiceResult.Error -> error(serviceResult.errorDTO)
            is ServiceResult.UnknownError -> error(null)
        }
    }

    override suspend fun getCoinDetails(
        cod: String,
        completion: suspend (CoinDetailDTO) -> Unit,
        error: suspend (ErrorResponseDTO?) -> Unit
    ) {
        when(val serviceResult = makeServiceRequest { coinService.getCoinDetails(cod) }) {
            is ServiceResult.Success -> {
                coinDetail = serviceResult.body
                completion(serviceResult.body)
            }
            is ServiceResult.Error -> error(serviceResult.errorDTO)
            is ServiceResult.UnknownError -> error(null)
        }
    }
}