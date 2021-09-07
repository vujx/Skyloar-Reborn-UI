package com.example.domain.usecase.leaderboards

import com.example.data.model.leaderboards.NameValueEntityItem
import com.example.domain.repository.leaderboard.LeaderboardRepository

class GetMonthRanges(
    private val leaderboardsRepo: LeaderboardRepository
) {

    suspend fun execute(
        path: String
    ): List<NameValueEntityItem> =
        try {
            val response = leaderboardsRepo.getRange(path)
            if (response.code() == 200)
                response.body() ?: emptyList()
            else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
}