package com.example.domain.usecase.leaderboards.pvp

import com.example.domain.repository.leaderboard.pvp.PvPRepository
import java.lang.Exception

class GetNumOfPvPSearchResult(private val pvpRepo: PvPRepository) {

    suspend fun execute(
        type: Int,
        month: Int
    ): Int =
        try {
            val response = pvpRepo.getNumOfPvPSearchResult(type, month)

            if (response.code() == 200)
                response.body()?.count ?: 0
            else 0
        } catch (e: Exception) {
            0
        }
}