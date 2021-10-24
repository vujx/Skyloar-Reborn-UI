package com.example.presentation.ui.leaderboards.leaderboards

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LeaderboardsAdapter : RecyclerView.Adapter<LeaderBoardsViewHolder>() {

  private val listOfLeaderboards = mutableListOf<LeaderBoards>()

  fun setList(list: List<LeaderBoards>) {
    listOfLeaderboards.clear()
    listOfLeaderboards.addAll(list)
    notifyDataSetChanged()
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardsViewHolder {
    val item = LeaderboardsItem(parent.context)
    return LeaderBoardsViewHolder(item)
  }

  override fun onBindViewHolder(holder: LeaderBoardsViewHolder, position: Int) {
    holder.bind(listOfLeaderboards[position])
  }

  override fun getItemCount(): Int = listOfLeaderboards.size
}