package com.example.domain.repository.leaderboard.pve

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvEEntityItem
import retrofit2.Response

interface PvENetworkDataSource {

    suspend fun getNumOfPvESearchResult(
        type: Int,
        players: Int,
        map: Int,
        month: Int
    ): Response<NumberOfSearchResultsEntity>

    suspend fun getPvEPlayers(
        type: Int,
        players: Int,
        map: Int,
        month: Int,
        page: Int,
        number: Int
    ): Response<List<PlayerPvEEntityItem>>
}
