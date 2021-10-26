package com.example.presentation.ui.leaderboards.adapter.pve

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.PvEPlayer
import com.example.presentation.MainActivity

class PvEAdapter : RecyclerView.Adapter<PvEViewHolder>() {

  private val listOfPvEPlayer = mutableListOf<PvEPlayer>()

  fun setList(list: List<PvEPlayer>) {
    listOfPvEPlayer.clear()
    listOfPvEPlayer.addAll(list)
    notifyDataSetChanged()
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PvEViewHolder {
    val pveItem = PvEItem(parent.context)
    return PvEViewHolder(pveItem)
  }

  override fun onBindViewHolder(holder: PvEViewHolder, position: Int) {
    listOfPvEPlayer[position].apply {
      holder.bind(players.toString(), time, difficultyStr)
    }
  }

  override fun getItemCount(): Int = listOfPvEPlayer.size
}
