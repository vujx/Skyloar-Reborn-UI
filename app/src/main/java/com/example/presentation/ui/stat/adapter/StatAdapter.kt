package com.example.presentation.ui.stat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class StatAdapter : RecyclerView.Adapter<StatViewHolder>() {

  private val listOfStatValue = mutableListOf<Long?>()

  fun setListOfStatValues(list: List<Long?>) {
    listOfStatValue.clear()
    listOfStatValue.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
    val statView = StatItem(parent.context)
    return StatViewHolder(statView)
  }

  override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
    holder.bind(listOfStatValue[position], position)
  }

  override fun getItemCount(): Int = listOfStatValue.size
}
