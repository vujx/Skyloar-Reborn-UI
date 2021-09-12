package com.example.domain.repository.leaderboard

class LeaderboardRepository(private val leaderboardsData: LeaderboardsNetworkDataSource) {

    suspend fun getRange(path: String) = leaderboardsData.getRange(path)
}
