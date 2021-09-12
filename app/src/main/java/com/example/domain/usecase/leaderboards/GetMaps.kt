package com.example.domain.usecase.leaderboards

import com.example.domain.repository.leaderboard.LeaderboardRepository

class GetMaps(
    private val leaderboardsRepo: LeaderboardRepository
) {

    suspend fun execute(
        type: Int
    ): MutableMap<Int, String> {

        val map = mutableMapOf<Int, String>()

        return try {
            val response = leaderboardsRepo.getMaps(type)

            if (response.code() == 200) {
                response.body()?.let { result ->
                    result.forEach {
                        map[it.value] = it.name
                    }
                }
                map
            } else
                map
        } catch (e: Exception) {
            map
        }
    }

}