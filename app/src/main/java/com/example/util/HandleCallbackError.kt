package com.example.util

import com.example.App
import com.example.domain.usecase.BaseUseCase
import java.net.ConnectException
import java.net.UnknownHostException

class HandleCallbackError<out R> {

    fun handleOnErrorCallback(e: Exception, callback: BaseUseCase.Callback<R>) {
        when (e) {
            is UnknownHostException ->
                callback.onError(App.getStringResource(com.example.R.string.unknown_host))
            is ConnectException ->
                callback.onError(App.getStringResource(com.example.R.string.check_internet))
            else ->
                callback.onError(App.getStringResource(com.example.R.string.unexpected_error))
        }
    }
}
