package com.example.data.repository.stat

import com.example.data.model.stat.StatEntity
import com.example.data.network.AuctionStatService
import com.example.data.network.safeApiCall
import com.example.domain.repository.stat.StatNetworkDataSource
import com.example.util.Result

class DefaultStatRepository(private val statService: AuctionStatService) :
  StatNetworkDataSource {

  override suspend fun getCount(path: String): Result<StatEntity> {
    return safeApiCall(
      {
        statService.getCount(path).body()!!
      }
    )
  }

  override suspend fun getCountWithMultiplePaths(path1: String, path2: String): Result<StatEntity> {
    return safeApiCall(
      {
        statService.getCountWithMultiplePath(path1, path2).body()!!
      }
    )
  }
}
