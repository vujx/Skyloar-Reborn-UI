package com.example.presentation.ui.stat.adapter

import androidx.recyclerview.widget.RecyclerView

class StatViewHolder(
  private val itemStat: StatItem
) : RecyclerView.ViewHolder(itemStat) {

  fun bind(
    statValue: Long?,
    position: Int,
  ) {
    itemStat.bind(statValue, position)
  }
}
