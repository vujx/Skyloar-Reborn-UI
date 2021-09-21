package com.example.presentation.ui.stat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.Dictionary
import com.example.R
import com.example.databinding.ItemStatBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatAdapter : RecyclerView.Adapter<StatViewHolder>(), KoinComponent {

  private val listOfStatValue = mutableListOf<Long?>()
  private val dictionary: Dictionary by inject()

  fun setListOfStatValues(list: List<Long?>) {
    listOfStatValue.clear()
    listOfStatValue.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
    val binding: ItemStatBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      R.layout.item_stat,
      parent,
      false
    )
    return StatViewHolder(binding)
  }

  override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
    holder.itemStat.statTitle =
      dictionary.getStringArray(R.array.statTitle)[position].toString()
    holder.itemStat.statValue = listOfStatValue[position]
  }

  override fun getItemCount(): Int = listOfStatValue.size
}
