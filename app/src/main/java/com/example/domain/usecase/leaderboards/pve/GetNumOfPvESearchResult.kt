package com.example.domain.usecase.leaderboards.pve

import com.example.domain.repository.leaderboard.pve.PvERepository

class GetNumOfPvESearchResult(private val pveRepo: PvERepository) {

    suspend fun execute(
        type: Int,
        players: Int,
        map: Int,
        month: Int
    ) =
        try {
            val response = pveRepo.getNumOfPvESearchResult(type, players, map, month)
            if (response.code() == 200) {
                response.body()?.let { result ->
                    result.count
                } ?: 0
            } else 0
        } catch (e: Exception) {
            0
        }
}
