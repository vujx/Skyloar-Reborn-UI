package com.example.domain.usecase.leaderboards.pvp

import com.example.domain.model.PvPPlayer
import com.example.domain.repository.leaderboard.pvp.PvPRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.util.Result

class GetPvPPlayers(
  private val pvpRepo: PvPRepository
) {

  suspend operator fun invoke(params: List<Any>): Result<List<PvPPlayer>?> = withContext(Dispatchers.IO) {
    pvpRepo.getPvPPlayers(
      params[0] as String,
      params[1] as Int,
      params[2] as Int,
      params[3] as Int
    )
  }
}
