package com.example.domain.repository.stat

class StatRepository(private val statData: StatNetworkDataSource) {

  suspend fun getCount(path: String) =
    statData.getCount(path)

  suspend fun getCountWithMultiplePaths(path1: String, path2: String) =
    statData.getCountWithMultiplePaths(path1, path2)
}
