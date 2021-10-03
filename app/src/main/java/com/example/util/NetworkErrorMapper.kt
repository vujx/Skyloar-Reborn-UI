package com.example.util

import android.system.ErrnoException
import android.util.Log
import com.example.domain.error.ErrorEntity
import com.example.domain.error.ErrorMapper
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.HttpURLConnection

class NetworkErrorMapper : ErrorMapper {

  override fun map(throwable: Throwable, errorType: Class<*>?) =
    when (throwable) {
      is IOException -> {
        Log.d("ispsiKaje", "sa")
        ErrorEntity.Network
      }
      is ConnectException -> ErrorEntity.Network
      is ErrnoException -> ErrorEntity.Network
      is HttpException -> {
        val code = throwable.code()
        if (errorType == null) {
          errorEntityByStatusCode(code)
        } else {
          val errorResponse = convertErrorBody(throwable, errorType)
          ErrorEntity.Custom(code, errorResponse)
        }
      }
      else -> {
        ErrorEntity.Unknown
      }
    }

  private fun errorEntityByStatusCode(code: Int) = when (code) {
    HttpURLConnection.HTTP_NOT_FOUND -> ErrorEntity.NotFound
    HttpURLConnection.HTTP_FORBIDDEN -> ErrorEntity.AccessDenied
    HttpURLConnection.HTTP_UNAVAILABLE -> ErrorEntity.ServiceUnavailable
    else -> {
      ErrorEntity.Unknown
    }
  }

  @Suppress("TooGenericExceptionCaught")
  private fun <T : Any> convertErrorBody(throwable: HttpException, clazz: Class<T>): T? {
    return try {
      throwable.response()?.errorBody()?.string()?.let {
        val moshiAdapter = Moshi.Builder().build().adapter(clazz)
        moshiAdapter.fromJson(it)
      }
    } catch (exception: Exception) {
      null
    }
  }
}
