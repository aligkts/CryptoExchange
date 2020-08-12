package com.aligkts.cryptoexchange.model.service

import com.aligkts.cryptoexchange.model.dto.response.ErrorResponseDTO

/**
 * Created by Ali Göktaş on 11.08.2020
 */
sealed class ServiceResult<out T> {
    data class Success<out T>(val body: T): ServiceResult<T>()
    data class Error(val errorDTO: ErrorResponseDTO): ServiceResult<Nothing>()
    object UnknownError: ServiceResult<Nothing>()
}