package com.example.presentation.ui.leaderboards.adapter.pvp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.PvPPlayer

class PvPAdapter : RecyclerView.Adapter<PvPViewHolder>() {

  private val listOfPvPPlayers = mutableListOf<PvPPlayer>()

  fun setListOfPvPPlayers(list: List<PvPPlayer>) {
    listOfPvPPlayers.clear()
    listOfPvPPlayers.addAll(list)
    notifyDataSetChanged()
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PvPViewHolder {
    val pvpView = PvPItem(parent.context)
    return PvPViewHolder(pvpView)
  }

  override fun onBindViewHolder(holder: PvPViewHolder, position: Int) {
    holder.bind(listOfPvPPlayers[position], position)
  }

  override fun getItemCount(): Int = listOfPvPPlayers.size
}
