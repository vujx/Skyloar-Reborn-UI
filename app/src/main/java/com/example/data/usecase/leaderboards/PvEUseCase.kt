package com.example.data.usecase.leaderboards

import com.example.domain.usecase.leaderboards.pve.GetNumOfPvESearchResult
import com.example.domain.usecase.leaderboards.pve.GetPvEPlayers

data class PvEUseCase(
    val getPvPPlayers: GetPvEPlayers,
    val getNumOfPvPSearchResult: GetNumOfPvESearchResult
)