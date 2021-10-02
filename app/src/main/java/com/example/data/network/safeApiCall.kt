package com.example.data.network

import com.example.domain.error.ErrorEntity
import com.example.domain.error.ErrorMapper
import com.example.util.Result

class NetworkResponseHelper(private val errorMapper: ErrorMapper) {

  @Suppress("TooGenericExceptionCaught")
  suspend fun <SuccessModel> safeApiCall(
    apiCall: suspend () -> SuccessModel,
    errorType: Class<*>? = null
  ): Result<SuccessModel> {
    return try {
      Result.Success(apiCall())
    } catch (throwable: Throwable) {
      Result.Error(errorMapper.map(throwable, errorType))
    }
  }
}

