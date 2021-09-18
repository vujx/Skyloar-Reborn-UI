package com.example.domain.usecase.leaderboards

import com.example.domain.repository.leaderboard.LeaderboardRepository
import com.example.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRanges(
  private val leaderboardsRepo: LeaderboardRepository
) {

  suspend operator fun invoke(params: String): Result<MutableMap<Int, String>> = withContext(Dispatchers.IO) {
    leaderboardsRepo.getRange(params)
  }
}
