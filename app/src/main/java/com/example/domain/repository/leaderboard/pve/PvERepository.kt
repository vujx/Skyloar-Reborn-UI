package com.example.domain.repository.leaderboard.pve

class PvERepository(private val pveRepository: PvENetworkDataSource) {

  suspend fun getNumOfPvESearchResult(
    type: Int,
    players: Int,
    map: Int,
    month: Int
  ) = pveRepository.getNumOfPvESearchResult(type, players, map, month)

  suspend fun getPvEPlayers(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: Int,
    number: Int
  ) = pveRepository.getPvEPlayers(type, players, map, month, page, number)
}
