package com.example.data.repository.leaderboards

import com.example.data.mapper.PvEPlayerMapper
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.network.LeaderboardService
import com.example.data.network.NetworkResponseHelper
import com.example.domain.model.PvEPlayer
import com.example.domain.repository.leaderboard.pve.PvENetworkDataSource
import com.example.util.CustomException
import com.example.util.Result

class DefaultPvERepository(
  private val leaderboardService: LeaderboardService,
  private val pveMapper: PvEPlayerMapper,
  private val networkResponseHelper: NetworkResponseHelper,
) :
  PvENetworkDataSource {

  override suspend fun getNumOfPvESearchResult(
    type: Int,
    players: Int,
    map: Int,
    month: Int
  ): Result<NumberOfSearchResultsEntity> {
    return networkResponseHelper.safeApiCall(
      {
        val response = leaderboardService.getPvECount(
          type = type,
          players = players,
          map = map,
          month = month
        )
        if (response.code() == 401) throw CustomException("Backend is currently cashing data!")
        else response.body()!!
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
    return networkResponseHelper.safeApiCall(
      {
        val response = leaderboardService.getListOfPvEPlayers(
          type = type,
          players = players,
          map = map,
          month = month,
          page = page,
          number = number
        )
        if (response.code() == 401) throw CustomException("Backend is currently cashing data!")
        else response.body()!!.map { pveMapper.mapFromEntity(it) }
      }
    )
  }
}
