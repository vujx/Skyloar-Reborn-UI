package com.example.domain.usecase.stat

import com.example.Dictionary
import com.example.R
import com.example.data.model.stat.StatEntity
import com.example.domain.error.ErrorEntity
import com.example.domain.repository.stat.StatRepository
import com.example.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.net.ConnectException

class GetStatValues(
  private val statRepo: StatRepository,
  private val dictionary: Dictionary,
) {

  private var countFailure = 0
  private var listOfStatValues = mutableListOf<StatEntity?>()

  suspend operator fun invoke() =
    withContext(Dispatchers.IO) {
      listOfStatValues.clear()
      try {
        withTimeoutOrNull(5000) {
          dictionary.getStringArray(R.array.statPaths).map { path ->
            if (path.contains("/")) {
              async {
                val response = statRepo.getCountWithMultiplePaths(
                  path.substring(0, path.indexOf('/')),
                  path.substring(
                    path.substring(
                      0,
                      path.indexOf('/')
                    ).length + 1
                  )
                )
                when (response) {
                  is Result.Success -> {
                    listOfStatValues.add(response.data)
                  }
                  is Result.Error -> {
                    countFailure++
                    if (response.error == ErrorEntity.Network) throw ConnectException()
                    else if (countFailure > 4) throw Exception()
                    else listOfStatValues.add(null)
                  }
                }
              }
            } else async {
              when (val response = statRepo.getCount(path)) {
                is Result.Success -> {
                  listOfStatValues.add(response.data)
                }
                is Result.Error -> {
                  countFailure++
                  if (response.error == ErrorEntity.Network) throw ConnectException()
                  else if (countFailure > 4) throw Exception()
                  else listOfStatValues.add(null)
                }
              }
            }
          }.awaitAll()
        } ?: Result.Error(ErrorEntity.ServiceUnavailable)

        return@withContext Result.Success(listOfStatValues)
      } catch (e: ConnectException) {
        return@withContext Result.Error(ErrorEntity.Network)
      } catch (e: Exception) {
        return@withContext Result.Error(ErrorEntity.NotFound)
      }
    }
}
