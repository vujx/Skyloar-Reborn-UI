package com.example.domain.repository.leaderboard.pvp

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import retrofit2.Response

interface PvPNetworkDataSource {

    suspend fun getNumOfPvPSearchResult(
        type: Int,
        month: Int
    ): Response<NumberOfSearchResultsEntity>

    suspend fun getPvPPlayers(
        type: Int,
        month: Int,
        page: Int,
        number: Int
    ): Response<List<PlayerPvPEntityItem>>
}