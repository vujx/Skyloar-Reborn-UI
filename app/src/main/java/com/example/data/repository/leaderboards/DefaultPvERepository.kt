package com.example.data.repository.leaderboards

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.data.network.LeaderboardService
import com.example.domain.repository.leaderboard.pve.PvENetworkDataSource
import retrofit2.Response

class DefaultPvERepository(private val leaderboardService: LeaderboardService) :
    PvENetworkDataSource {

    override suspend fun getNumOfPvESearchResult(
        type: Int,
        players: Int,
        map: Int,
        month: Int
    ): Response<NumberOfSearchResultsEntity> =
        leaderboardService.getPvECount(type, players, map, month)

    override suspend fun getPvEPlayers(
        type: Int,
        players: Int,
        map: Int,
        month: Int,
        page: Int,
        number: Int
    ): Response<List<PlayerPvEEntityItem>> =
        leaderboardService.getListOfPvEPlayers(type, players, map, month, page, number)
}
