package com.example.domain.repository.leaderboard.pvp

import com.example.domain.model.PvPPlayer
import com.example.util.Result
class PvPRepository(private val pvpData: PvPNetworkDataSource) {

  suspend fun getNumOfPvPSearchResult(
    type: String,
    month: Int
  ) = pvpData.getNumOfPvPSearchResult(type, month)

  suspend fun getPvPPlayers(
    type: String,
    month: Int,
    page: Int,
    number: Int
  ): Result<List<PvPPlayer>?> = pvpData.getPvPPlayers(type, month, page, number)
}
