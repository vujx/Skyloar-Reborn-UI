package com.example.presentation.ui.leaderboards.leaderboards

import androidx.recyclerview.widget.RecyclerView

class LeaderBoardsViewHolder(
  private val item: LeaderboardsItem
  ) : RecyclerView.ViewHolder(item) {

    fun bind(
      leaderBoards: LeaderBoards,
      position: Int,
      onDocClick: ((String) -> Unit)?,
      onLeaderboardsClick: ((Int) -> Unit)?,
      onLeaderboardsPvPClick: ((String) -> Unit)?,
      isLeaderBoards: Boolean
    ) {
      item.bind(leaderBoards, position, isLeaderBoards,{
        if (onDocClick != null) {
          onDocClick(it)
        }
      }, {
        if(onLeaderboardsClick != null) {
          onLeaderboardsClick(it)
        }
      }, {
        if(onLeaderboardsPvPClick != null) {
          onLeaderboardsPvPClick(it)
        }
      })
    }
}