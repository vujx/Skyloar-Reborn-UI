package com.example.presentation.ui.stat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.stat.StatEntity

class StatAdapter : RecyclerView.Adapter<StatViewHolder>() {

  private var listOfStatValue = mutableMapOf<String, StatEntity?>()

  fun setListOfStatValues(list: MutableMap<String, StatEntity?>) {
    listOfStatValue = list
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
    val statView = StatItem(parent.context)
    return StatViewHolder(statView)
  }

  override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
    holder.bind(listOfStatValue, position)
  }

  override fun getItemCount(): Int = listOfStatValue.size
}
