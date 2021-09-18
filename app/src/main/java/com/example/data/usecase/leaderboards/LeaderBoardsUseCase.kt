package com.example.data.usecase.leaderboards

import com.example.domain.usecase.leaderboards.GetMaps
import com.example.domain.usecase.leaderboards.GetRanges

data class LeaderBoardsUseCase(
  val getRanges: GetRanges,
  val getMaps: GetMaps
)
