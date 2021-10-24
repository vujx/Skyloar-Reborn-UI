package com.example.presentation.ui.leaderboards.viewmodel

import com.example.R
import com.example.presentation.ui.leaderboards.leaderboards.LeaderBoards

class LeaderBoardsMapper {

  fun map(): List<LeaderBoards> {
    return listOf(
      LeaderBoards("1-player PvE", R.drawable.leaderboards_1_pve, "Table #1"),
      LeaderBoards("2-player PvE", R.drawable.leaderboards_2_pve, "Table #2"),
      LeaderBoards("4-player PvE", R.drawable.leaderboards_4_pve, "Table #3"),
      LeaderBoards("12-player PvE", R.drawable.leaderboarads_12_pve, "Table #4"),
      LeaderBoards("1-player PvP", R.drawable.leaderboard_1vs1_pvp, "Table #5"),
      LeaderBoards("2-player PvP", R.drawable.leaderboard_1, "Table #6"),
    )
  }
}