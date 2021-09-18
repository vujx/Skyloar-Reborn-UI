package com.example.domain.repository.leaderboard

import com.example.util.Result

class LeaderboardRepository(private val leaderboardsData: LeaderboardsNetworkDataSource) {

  suspend fun getRange(path: String): Result<MutableMap<Int, String>> = leaderboardsData.getRange(path)

  suspend fun getMaps(type: Int) = leaderboardsData.getMaps(type)
}
