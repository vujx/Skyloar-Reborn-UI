package com.example.data.repository.leaderboards

import com.example.data.network.LeaderboardService
import com.example.data.network.safeApiCall
import com.example.domain.repository.leaderboard.LeaderboardsNetworkDataSource
import com.example.util.Result

class DefaultLeaderboardsRepository(private val leaderboardService: LeaderboardService) :
  LeaderboardsNetworkDataSource {

  override suspend fun getRange(path: String): Result<MutableMap<Int, String>> {
    return safeApiCall({
      val map = mutableMapOf<Int, String>()
      leaderboardService.getRanges(path).body()!!.forEach { result ->
        map[result.value] = result.name
      }
      map
    })
  }

  override suspend fun getMaps(type: Int): Result<MutableMap<Int, String>> {
    return safeApiCall({
      val map = mutableMapOf<Int, String>()
      leaderboardService.getMapsByType(type).body()!!.forEach { result ->
        map[result.value] = result.name
      }
      map
    })
  }
}
