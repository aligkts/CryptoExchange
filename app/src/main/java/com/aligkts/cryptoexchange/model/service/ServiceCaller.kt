package com.aligkts.cryptoexchange.model.service

import com.aligkts.cryptoexchange.model.dto.response.ErrorResponseDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

/**
 * Created by Ali Göktaş on 11.08.2020
 */
suspend fun <T> makeServiceRequest(serviceCall: suspend () -> T): ServiceResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            ServiceResult.Success(serviceCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ServiceResult.UnknownError
                is HttpException -> {
                    val errorResponse = convertErrorBody(throwable)
                    if (errorResponse != null) {
                        ServiceResult.Error(errorResponse)
                    } else {
                        ServiceResult.UnknownError
                    }
                }
                else -> {
                    ServiceResult.UnknownError
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponseDTO? {
    return try {
        Gson().fromJson(
            throwable.response()?.errorBody()?.charStream(),
            ErrorResponseDTO::class.java
        )
    } catch (exception: Exception) {
        null
    }
}