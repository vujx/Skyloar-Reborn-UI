package com.example.data.network

import android.util.Log
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
      Log.d("isw", throwable.toString())
      Result.Error(errorMapper.map(throwable, errorType))
    }
  }
}
