package com.example.domain.usecase.stat

import com.example.Dictionary
import com.example.R
import com.example.data.model.stat.StatEntity
import com.example.domain.repository.stat.StatRepository
import com.example.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetStatValues(
  private val statRepo: StatRepository,
  private val dictionary: Dictionary
) {

  suspend operator fun invoke(): List<Result<StatEntity>> =
    withContext(Dispatchers.IO) {
      dictionary.getStringArray(R.array.statPaths).map { path ->
        if (path.contains("/")) {
          async {
            statRepo.getCountWithMultiplePaths(
              path.substring(0, path.indexOf('/')),
              path.substring(
                path.substring(
                  0,
                  path.indexOf('/')
                ).length + 1
              )
            )
          }
        } else async { statRepo.getCount(path) }
      }.awaitAll()
    }
}
