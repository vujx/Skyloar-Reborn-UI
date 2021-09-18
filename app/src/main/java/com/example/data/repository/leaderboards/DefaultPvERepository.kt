package com.example.data.repository.leaderboards

import com.example.data.mapper.PvEPlayerMapper
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.data.network.LeaderboardService
import com.example.data.network.safeApiCall
import com.example.domain.model.PvEPlayer
import com.example.domain.repository.leaderboard.pve.PvENetworkDataSource
import com.example.util.Result
import retrofit2.Response

class DefaultPvERepository(
  private val leaderboardService: LeaderboardService,
  private val pveMapper: PvEPlayerMapper
) :
  PvENetworkDataSource {

  override suspend fun getNumOfPvESearchResult(
    type: Int,
    players: Int,
    map: Int,
    month: Int
  ): Result<NumberOfSearchResultsEntity> {
    return safeApiCall(
      {
        leaderboardService.getPvECount(
          type,
          players,
          map,
          month
        ).body()!!
      }
    )
  }

  override suspend fun getPvEPlayers(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: Int,
    number: Int
  ): Result<List<PvEPlayer>?> {
    return safeApiCall(
      {
        val response = leaderboardService.getListOfPvEPlayers(
          type,
          players,
          map,
          month,
          page, number
        )
        if (response.code() == 401) null
        else response.body()!!.map { pveMapper.mapFromEntity(it) }
      }
    )
  }
}
