package com.example.domain.usecase.stat

import com.example.App
import com.example.R
import com.example.data.model.stat.StatEntity
import com.example.domain.repository.stat.StatRepository
import com.example.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class GetStatValues(
  private val statRepo: StatRepository
) {

  private val listOfResponse = LongArray(39)

  suspend operator fun invoke(): List<Long> {
    withContext(Dispatchers.IO) {
      (0..38).asyncAll {
        val path = App.getResources.getStringArray(R.array.statPaths)[it]
        if (path.contains("/")) {
          handleResponse(
            it,
            statRepo.getCountWithMultiplePaths(
              path.substring(0, path.indexOf('/')),
              path.substring(
                path.substring(
                  0,
                  path.indexOf('/')
                ).length + 1
              )
            )
          )
        } else handleResponse(
          it,
          statRepo.getCount(App.getResources.getStringArray(R.array.statPaths)[it])
        )
      }
    }
    return listOfResponse.toList()
  }

  private fun handleResponse(it: Int, response: Result<StatEntity>) {
    when (response) {
      is Result.Success -> addCountNumberToList(response.data.count as Double, listOfResponse, it)
      is Result.Error -> addCountNumberToList(-1.0, listOfResponse, it)
    }
  }

  private fun addCountNumberToList(
    response: Double,
    listOfResponse: LongArray,
    it: Int
  ) {
    listOfResponse[it] = response.toLong()
  }

  private suspend fun <T, V> Iterable<T>.asyncAll(coroutine: suspend (T) -> V): Iterable<V> =
    coroutineScope {
      this@asyncAll.map { async { coroutine(it) } }.awaitAll()
    }
}
