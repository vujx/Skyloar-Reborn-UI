package com.example.domain.repository.leaderboard

import com.example.util.Result

interface LeaderboardsNetworkDataSource {

  suspend fun getRange(path: String): Result<MutableMap<Int, String>>

  suspend fun getMaps(type: Int): Result<MutableMap<Int, String>>
}
