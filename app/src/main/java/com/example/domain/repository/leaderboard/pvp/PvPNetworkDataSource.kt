package com.example.domain.repository.leaderboard.pvp

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.domain.model.PvPPlayer
import com.example.util.Result
interface PvPNetworkDataSource {

  suspend fun getNumOfPvPSearchResult(
    type: String,
    month: Int
  ): Result<NumberOfSearchResultsEntity>

  suspend fun getPvPPlayers(
    type: String,
    month: Int,
    page: Int,
    number: Int
  ): Result<List<PvPPlayer>?>
}
