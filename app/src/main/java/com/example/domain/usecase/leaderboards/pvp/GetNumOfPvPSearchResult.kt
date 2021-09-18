package com.example.domain.usecase.leaderboards.pvp

import com.example.domain.repository.leaderboard.pvp.PvPRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNumOfPvPSearchResult(private val pvpRepo: PvPRepository) {

  suspend operator fun invoke(type: String, month: Int) = withContext(Dispatchers.IO) {
    pvpRepo.getNumOfPvPSearchResult(type, month)
  }
}
