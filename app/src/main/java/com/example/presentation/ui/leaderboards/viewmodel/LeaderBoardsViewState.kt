package com.example.presentation.ui.leaderboards.viewmodel

import com.example.presentation.ui.leaderboards.leaderboards.LeaderBoards

sealed class LeaderBoardsViewState{
  data class Content(
     val data: List<LeaderBoards>
  ) : LeaderBoardsViewState()
  object Loading : LeaderBoardsViewState()
  data class NavigateToPvePlayers(
    val type: Int
  ) : LeaderBoardsViewState()
}


