package com.example.presentation.ui.stat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.App
import com.example.R
import com.example.databinding.ItemStatBinding

class StatAdapter : RecyclerView.Adapter<StatViewHolder>() {

    private val listOfStatValue = mutableListOf<Int>()

    fun setListOfStatValues(list: List<Int>) {
        listOfStatValue.clear()
        listOfStatValue.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val binding: ItemStatBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_auction,
            parent,
            false
        )
        return StatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.itemStat.statTitle = App.getResources.getStringArray(R.array.statTitle)[position].toString()
        holder.itemStat.statValue = listOfStatValue[position]
    }

    override fun getItemCount(): Int = listOfStatValue.size
}