package com.example.domain.usecase.leaderboards.pve

import com.example.domain.repository.leaderboard.pve.PvERepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNumOfPvESearchResult(private val pveRepo: PvERepository) {

  suspend operator fun invoke(
    type: Int,
    players: Int,
    map: Int,
    month: Int
  ) = withContext(Dispatchers.IO) {
    pveRepo.getNumOfPvESearchResult(
      type,
      players,
      map,
      month
    )
  }
}
