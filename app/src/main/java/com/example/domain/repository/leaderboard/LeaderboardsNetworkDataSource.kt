package com.example.domain.repository.leaderboard

import com.example.data.model.leaderboards.NameValueEntityItem
import com.example.data.model.leaderboards.TimeToNextRefreshEntity
import retrofit2.Response

interface LeaderboardsNetworkDataSource {

    suspend fun getRange(path: String): Response<List<NameValueEntityItem>>

    suspend fun getTimeToRefresh(): Response<TimeToNextRefreshEntity>
}