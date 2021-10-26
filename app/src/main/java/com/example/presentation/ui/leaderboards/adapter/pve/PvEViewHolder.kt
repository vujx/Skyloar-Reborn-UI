package com.example.presentation.ui.leaderboards.adapter.pve

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.PvEPlayer

class PvEViewHolder(
  private val itemPvE: PvEItem
) : RecyclerView.ViewHolder(itemPvE) {

  fun bind(
    pvePlayer: String,
    time: String,
    difficulty: String
  ) {
    itemPvE.bind(
      pvePlayer,
      time,
      difficulty
    )
  }
}
