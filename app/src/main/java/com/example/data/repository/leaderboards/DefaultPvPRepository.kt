package com.example.data.repository.leaderboards

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.data.network.LeaderboardService
import com.example.domain.repository.leaderboard.pvp.PvPNetworkDataSource
import retrofit2.Response

class DefaultPvPRepository(private val leaderboardService: LeaderboardService) :
    PvPNetworkDataSource {
    override suspend fun getNumOfPvPSearchResult(
        type: String,
        month: Int
    ): Response<NumberOfSearchResultsEntity> = leaderboardService.getPvPCount(type, month)

    override suspend fun getPvPPlayers(
        type: String,
        month: Int,
        page: Int,
        number: Int
    ): Response<List<PlayerPvPEntityItem>> =
        leaderboardService.getListOfPvPPlayers(type, month, page, number)
}
