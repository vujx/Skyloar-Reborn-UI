package com.example.domain.repository.leaderboard.pvp

class PvPRepository(private val pvpData: PvPNetworkDataSource) {

    suspend fun getNumOfPvPSearchResult(
        type: Int,
        month: Int
    ) = pvpData.getNumOfPvPSearchResult(type, month)

    suspend fun getPvPPlayers(
        type: Int,
        month: Int,
        page: Int,
        number: Int
    ) = pvpData.getPvPPlayers(type, month, page, number)
}