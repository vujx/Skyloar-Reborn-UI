package com.example.presentation.ui.leaderboards.adapter.pve

import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemPvePlayersBinding
import com.example.databinding.ItemPvePlayersInfoBinding
import com.example.domain.model.PvEPlayer

class PvEViewHolder(
  private val itemPvE: PvEItem
) : RecyclerView.ViewHolder(itemPvE) {

  fun bind(
    pvePlayer: PvEPlayer,
    difficulty: String
  ) {
    itemPvE.bind(
      pvePlayer,
      difficulty
    )
  }
}
