package com.example.presentation.ui.leaderboards.adapter.pvp

import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.PvPPlayer

class PvPViewHolder(
  private val itemPvP: PvPItem
) : RecyclerView.ViewHolder(itemPvP) {

  fun bind(pvpPlayer: PvPPlayer) {
    itemPvP.bind(pvpPlayer)
  }
}
