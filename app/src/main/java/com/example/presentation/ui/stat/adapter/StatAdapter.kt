package com.example.presentation.ui.stat.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.stat.StatEntity

class StatAdapter : RecyclerView.Adapter<StatViewHolder>() {

  private val listOfStatValue = mutableListOf<StatEntity?>()

  fun setListOfStatValues(list: List<StatEntity?>) {
    listOfStatValue.clear()
    listOfStatValue.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
    val statView = StatItem(parent.context)
    return StatViewHolder(statView)
  }

  override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
    Log.d("ispis", "${listOfStatValue[position]}")
    listOfStatValue[position]?.let { holder.bind(it, position) }
  }

  override fun getItemCount(): Int = listOfStatValue.size
}
