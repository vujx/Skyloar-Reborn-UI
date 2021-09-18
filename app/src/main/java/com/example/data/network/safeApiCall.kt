package com.example.data.network

import com.example.domain.error.ErrorEntity
import com.example.domain.error.ErrorMapper
import com.example.util.Result
var networkErrorMapper: ErrorMapper? = null

@Suppress("TooGenericExceptionCaught")
suspend fun <SuccessModel> safeApiCall(
  apiCall: suspend () -> SuccessModel,
  errorType: Class<*>? = null
): Result<SuccessModel> {
  return try {
    Result.Success(apiCall())
  } catch (throwable: Throwable) {
    if (networkErrorMapper != null) {
      Result.Error(networkErrorMapper!!.map(throwable, errorType))
    } else {
      Result.Error(ErrorEntity.Unknown)
    }
  }
}
