package com.example.presentation.ui.leaderboards.adapter.pve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.databinding.ItemPvePlayersBinding
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
    var diff = ""
    MainActivity.listOfDifficulties?.forEach {
      if (it.key == listOfPvEPlayer[position].difficulty)
        diff = it.value
    }
    holder.bind(listOfPvEPlayer[position], diff)
  }

  override fun getItemCount(): Int = listOfPvEPlayer.size
}
