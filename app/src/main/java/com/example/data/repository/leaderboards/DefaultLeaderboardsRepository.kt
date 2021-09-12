package com.example.data.repository.leaderboards

import com.example.data.model.leaderboards.NameValueEntityItem
import com.example.data.network.LeaderboardService
import com.example.domain.repository.leaderboard.LeaderboardsNetworkDataSource
import retrofit2.Response

class DefaultLeaderboardsRepository(private val leaderboardService: LeaderboardService) :
    LeaderboardsNetworkDataSource {

    override suspend fun getRange(path: String): Response<List<NameValueEntityItem>> =
        leaderboardService.getRanges(path)

    override suspend fun getMaps(type: Int): Response<List<NameValueEntityItem>> =
        leaderboardService.getMapsByType(type)
}
