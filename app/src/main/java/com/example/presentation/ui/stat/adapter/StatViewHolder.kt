package com.example.presentation.ui.stat.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.stat.StatEntity

class StatViewHolder(
  private val itemStat: StatItem
) : RecyclerView.ViewHolder(itemStat) {

  fun bind(
    statValue: MutableMap<String, StatEntity?>,
    position: Int,
  ) {
    itemStat.bind(statValue, position)
  }
}
