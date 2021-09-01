package com.example.domain.usecase

interface BaseUseCase<in P, out R> {
    interface Callback<in R> {
        fun onSuccess(result: R)
        fun onError(errorMessage: String)
    }

    suspend fun execute(params: P, callback: Callback<R>)
}
