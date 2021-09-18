package com.example.domain.repository.leaderboard.pve

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.domain.model.PvEPlayer
import com.example.util.Result

interface PvENetworkDataSource {

  suspend fun getNumOfPvESearchResult(
    type: Int,
    players: Int,
    map: Int,
    month: Int
  ): Result<NumberOfSearchResultsEntity>

  suspend fun getPvEPlayers(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: Int,
    number: Int
  ): Result<List<PvEPlayer>?>
}
