package com.example.util

import com.example.domain.error.ErrorEntity

sealed class Result<out T> {
  data class Success<out T>(val data: T) : Result<T>()
  data class Error(val error: ErrorEntity) : Result<Nothing>()
}