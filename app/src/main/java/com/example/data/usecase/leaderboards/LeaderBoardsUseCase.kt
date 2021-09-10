package com.example.data.usecase.leaderboards

import com.example.domain.usecase.leaderboards.GetRanges
import com.example.domain.usecase.leaderboards.GetTimeToRefresh

data class LeaderBoardsUseCase(
    val getRanges: GetRanges,
    val getTimeToRefresh: GetTimeToRefresh
)