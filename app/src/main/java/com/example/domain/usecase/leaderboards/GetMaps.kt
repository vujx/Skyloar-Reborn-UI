package com.example.domain.usecase.leaderboards

import com.example.domain.repository.leaderboard.LeaderboardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.util.Result

class GetMaps(
  private val leaderboardsRepo: LeaderboardRepository
) {

  suspend operator fun invoke(params: Int): Result<MutableMap<Int, String>> = withContext(Dispatchers.IO) {
    leaderboardsRepo.getMaps(params)
  }
}
