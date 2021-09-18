package com.example.data.repository.leaderboards

import com.example.data.mapper.PvPPlayerMapper
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.data.network.LeaderboardService
import com.example.data.network.safeApiCall
import com.example.domain.model.PvPPlayer
import com.example.domain.repository.leaderboard.pvp.PvPNetworkDataSource
import com.example.util.Result
import retrofit2.Response

class DefaultPvPRepository(
  private val leaderboardService: LeaderboardService,
  private val pvpMapper: PvPPlayerMapper
) :
  PvPNetworkDataSource {

  override suspend fun getNumOfPvPSearchResult(
    type: String,
    month: Int
  ): Result<NumberOfSearchResultsEntity> {
    return safeApiCall({
      leaderboardService.getPvPCount(type, month).body()!!
    })
  }

  override suspend fun getPvPPlayers(
    type: String,
    month: Int,
    page: Int,
    number: Int
  ): Result<List<PvPPlayer>?> {
    return safeApiCall(
      {
        val response = leaderboardService.getListOfPvPPlayers(type, month, page, number)
        if (response.code() == 401) null else response.body()!!.map { pvpMapper.mapFromEntity(it) }
      }
    )
  }
}
