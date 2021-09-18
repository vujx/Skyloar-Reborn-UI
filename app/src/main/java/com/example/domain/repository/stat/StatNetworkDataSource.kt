package com.example.domain.repository.stat

import com.example.data.model.stat.StatEntity
import com.example.util.Result

interface StatNetworkDataSource {

  suspend fun getCount(path: String): Result<StatEntity>

  suspend fun getCountWithMultiplePaths(path1: String, path2: String): Result<StatEntity>
}
