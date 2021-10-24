package com.example.presentation.ui.leaderboards.leaderboards

import androidx.recyclerview.widget.RecyclerView

class LeaderBoardsViewHolder(
  private val item: LeaderboardsItem
  ) : RecyclerView.ViewHolder(item) {

    fun bind(leaderBoards: LeaderBoards) {
      item.bind(leaderBoards)
    }
}