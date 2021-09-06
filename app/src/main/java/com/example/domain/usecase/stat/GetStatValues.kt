package com.example.domain.usecase.stat

import com.example.App
import com.example.R
import com.example.domain.repository.stat.StatRepository
import com.example.domain.usecase.BaseUseCase
import com.example.util.HandleCallbackError

class GetStatValues(
    private val statRepo: StatRepository
) : BaseUseCase<String?, List<Int>> {

    override suspend fun execute(params: String?, callback: BaseUseCase.Callback<List<Int>>) {
        try {
            val listOfResponse = mutableListOf<Int>()

            for (i: Int in 0 until 40) {
                val response =
                    statRepo.getCount(App.getResources.getStringArray(R.array.statPaths)[i].toString())
                when (response.code()) {
                    200 -> {
                        response.body()?.let { result ->
                            listOfResponse.add(result.count)
                        }
                    }
                }
            }
            if (listOfResponse.size == 39)
                callback.onSuccess(listOfResponse)
            else
                callback.onError(App.getStringResource(R.string.unexpected_error))
        } catch (e: Exception) {
            HandleCallbackError<List<Int>>().handleOnErrorCallback(e, callback)
        }
    }
}