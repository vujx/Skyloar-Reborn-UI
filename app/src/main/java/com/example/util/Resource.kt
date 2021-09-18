package com.example.util

sealed class Resource<out T> {
  data class Success<out R>(val value: R) : Resource<R>()
  data class Failure(val message: String) : Resource<Nothing>()
  class Loading<out R>() : Resource<R>()
  class Empty<out R>() : Resource<R>()
}
