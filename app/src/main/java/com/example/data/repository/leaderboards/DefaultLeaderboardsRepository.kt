package com.example.data.repository.leaderboards

import com.example.data.model.leaderboards.NameValueEntityItem
import com.example.data.model.leaderboards.TimeToNextRefreshEntity
import com.example.data.network.AuctionService
import com.example.domain.repository.leaderboard.LeaderboardsNetworkDataSource
import retrofit2.Response

class DefaultLeaderboardsRepository(private val auctionService: AuctionService): LeaderboardsNetworkDataSource {

    override suspend fun getRange(path: String): Response<List<NameValueEntityItem>> = auctionService.getRanges(path)

    override suspend fun getTimeToRefresh(): Response<TimeToNextRefreshEntity> = auctionService.getTimeToNextRefresh()
}