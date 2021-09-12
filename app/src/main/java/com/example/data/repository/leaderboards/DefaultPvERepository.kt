package com.example.data.repository.leaderboards

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.data.network.AuctionService
import com.example.domain.repository.leaderboard.pve.PvENetworkDataSource
import retrofit2.Response

class DefaultPvERepository(private val auctionService: AuctionService) : PvENetworkDataSource {

    override suspend fun getNumOfPvESearchResult(
        type: Int,
        players: Int,
        map: Int,
        month: Int
    ): Response<NumberOfSearchResultsEntity> = auctionService.getPvECount(type, players, map, month)

    override suspend fun getPvEPlayers(
        type: Int,
        players: Int,
        map: Int,
        month: Int,
        page: Int,
        number: Int
    ): Response<List<PlayerPvEEntityItem>> =
        auctionService.getListOfPvELeaderboard(type, players, map, month, page, number)
}