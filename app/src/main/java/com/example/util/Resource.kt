package com.example.util

import com.example.domain.error.ErrorEntity

sealed class Resource<out T> {
  data class Success<out R>(val value: R) : Resource<R>()
  data class Failure(val error: ErrorEntity) : Resource<Nothing>()
  class Loading<out R>() : Resource<R>()
  class Empty<out R>() : Resource<R>()
}
