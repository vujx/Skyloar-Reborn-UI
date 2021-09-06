package com.example.domain.usecase.stat

import com.example.App
import com.example.R
import com.example.data.model.stat.StatEntity
import com.example.domain.repository.stat.StatRepository
import com.example.domain.usecase.BaseUseCase
import com.example.util.HandleCallbackError
import kotlinx.coroutines.*
import retrofit2.Response

class GetStatValues(
    private val statRepo: StatRepository
) : BaseUseCase<String?, List<Long>> {

    private lateinit var response: Response<StatEntity>

    override suspend fun execute(params: String?, callback: BaseUseCase.Callback<List<Long>>) {
        val listOfResponse = LongArray(39)
        val jobs = mutableListOf<Job>()

        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            callback.onError(App.getStringResource(R.string.unexpected_error))
        }

        try {
            CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                (0..38).forEach {
                    val path = App.getResources.getStringArray(R.array.statPaths)[it]
                    if (path.contains("/")) {
                        try {
                            withTimeout(500) {
                                jobs += launch {
                                    response = statRepo.getCountWithMultiplePaths(
                                        path.substring(0, path.indexOf('/')),
                                        path.substring(
                                            path.substring(
                                                0,
                                                path.indexOf('/')
                                            ).length + 1
                                        )
                                    )
                                    addCountNumberToList(response, listOfResponse, it)
                                }
                            }
                        } catch (e: TimeoutCancellationException) {
                            listOfResponse[it] = -1
                        }
                    } else {
                        try {
                            withTimeout(500) {
                                jobs += launch {
                                    response =
                                        statRepo.getCount(App.getResources.getStringArray(R.array.statPaths)[it])
                                    addCountNumberToList(response, listOfResponse, it)
                                }
                            }
                        } catch (e: TimeoutCancellationException) {
                            listOfResponse[it] = -1
                        }
                    }
                }
                jobs.map {
                    it.join()
                }

                callback.onSuccess(listOfResponse.toList())
            }
        } catch (e: Exception) {
            HandleCallbackError<List<Long>>().handleOnErrorCallback(e, callback)
        }
    }

    private fun addCountNumberToList(
        response: Response<StatEntity>,
        listOfResponse: LongArray,
        it: Int
    ) {
        when (response.code()) {
            200 -> {
                response.body()?.let { result ->
                    listOfResponse[it] = (result.count as Double).toLong()
                }
            }
            404 -> listOfResponse[it] = -1
        }
    }
}
