package com.example.domain.usecase.leaderboards

import com.example.domain.repository.leaderboard.LeaderboardRepository

class GetTimeToRefresh(
    private val leaderboardsRepo: LeaderboardRepository
) {

    suspend fun execute(): Long =
        try {
            val response = leaderboardsRepo.getTimeToRefresh()
            if (response.code() == 200)
                response.body()?.time ?: -1
            else -1
        } catch (e: Exception) {
            -1
        }
}