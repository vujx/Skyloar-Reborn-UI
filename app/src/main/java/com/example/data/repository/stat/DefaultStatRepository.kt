package com.example.data.repository.stat

import com.example.data.model.stat.StatEntity
import com.example.data.network.AuctionStatService
import com.example.domain.repository.stat.StatNetworkDataSource
import retrofit2.Response

class DefaultStatRepository(private val statService: AuctionStatService) :
  StatNetworkDataSource {

  override suspend fun getCount(path: String): Response<StatEntity> =
    statService.getCount(path)

  override suspend fun getCountWithMultiplePaths(
    path1: String,
    path2: String
  ): Response<StatEntity> =
    statService.getCountWithMultiplePath(path1, path2)
}
