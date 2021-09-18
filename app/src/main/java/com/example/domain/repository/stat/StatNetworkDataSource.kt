package com.example.domain.repository.stat

import com.example.data.model.stat.StatEntity
import retrofit2.Response

interface StatNetworkDataSource {

  suspend fun getCount(path: String): Response<StatEntity>

  suspend fun getCountWithMultiplePaths(path1: String, path2: String): Response<StatEntity>
}
