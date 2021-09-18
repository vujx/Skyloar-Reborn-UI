package com.example.data.usecase.leaderboards

import com.example.domain.usecase.leaderboards.pvp.GetNumOfPvPSearchResult
import com.example.domain.usecase.leaderboards.pvp.GetPvPPlayers

data class PvPUseCase(
  val getPvPPlayers: GetPvPPlayers,
  val getNumOfPvPSearchResult: GetNumOfPvPSearchResult
)
