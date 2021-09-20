package com.example.presentation.ui.leaderboards.adapter.pvp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.databinding.ItemPvpPlayerBinding
import com.example.domain.model.PvPPlayer

class PvPAdapter : RecyclerView.Adapter<PvPViewHolder>() {

  private val listOfPvPPlayers = mutableListOf<PvPPlayer>()

  fun setListOfPvPPlayers(list: List<PvPPlayer>) {
    listOfPvPPlayers.clear()
    listOfPvPPlayers.addAll(list)
    notifyDataSetChanged()
  }
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PvPViewHolder {
    val binding: ItemPvpPlayerBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      R.layout.item_pvp_player,
      parent,
      false
    )
    return PvPViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PvPViewHolder, position: Int) {
    if(listOfPvPPlayers[position].totalMatches == null) holder.itemPvP.tvMatches.visibility = View.GONE
    else View.VISIBLE
    holder.itemPvP.player = listOfPvPPlayers[position]
  }

  override fun getItemCount(): Int = listOfPvPPlayers.size
}
