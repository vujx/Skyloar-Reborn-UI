package com.example.domain.usecase.stat

import com.example.App
import com.example.R
import com.example.data.model.stat.StatEntity
import com.example.domain.repository.stat.StatRepository
import com.example.domain.usecase.BaseUseCase
import com.example.util.HandleCallbackError
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.Response

class GetStatValues(
  private val statRepo: StatRepository
) : BaseUseCase<String?, List<Long>> {

  private lateinit var response: Response<StatEntity>

  override suspend fun execute(params: String?, callback: BaseUseCase.Callback<List<Long>>) {
    val listOfResponse = LongArray(39)

    try {
      (0..38).asyncAll {
        val path = App.getResources.getStringArray(R.array.statPaths)[it]
        if (path.contains("/")) {
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
        } else {
          response =
            statRepo.getCount(App.getResources.getStringArray(R.array.statPaths)[it])
          addCountNumberToList(response, listOfResponse, it)
        }
      }
      callback.onSuccess(listOfResponse.toList())
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

  private suspend fun <T, V> Iterable<T>.asyncAll(coroutine: suspend (T) -> V): Iterable<V> =
    coroutineScope {
      this@asyncAll.map { async { coroutine(it) } }.awaitAll()
    }
}
