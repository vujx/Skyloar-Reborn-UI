package com.example.domain.usecase.leaderboards.pve

import com.example.domain.repository.leaderboard.pve.PvERepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPvEPlayers(private val pveRepo: PvERepository) {

  suspend operator fun invoke(params: List<Int>) = withContext(Dispatchers.IO) {
    pveRepo.getPvEPlayers(
      params[0],
      params[1],
      params[2],
      params[3],
      params[4],
      params[5]
    )
  }
}
