package com.example.presentation.ui.auctions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.data.model.auction.AuctionEntityItem
import com.example.databinding.ItemAuctionBinding

class AuctionAdapter : RecyclerView.Adapter<AuctionViewHolder>() {

    private val listOfAuctions = mutableListOf<AuctionEntityItem>()

    fun setListOfAuctions(list: List<AuctionEntityItem>) {
        listOfAuctions.clear()
        listOfAuctions.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionViewHolder {
        val binding: ItemAuctionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_auction,
            parent,
            false
        )
        return AuctionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AuctionViewHolder, position: Int) {
        holder.itemAuction.auction = listOfAuctions[position]
    }

    override fun getItemCount(): Int = listOfAuctions.size
}
