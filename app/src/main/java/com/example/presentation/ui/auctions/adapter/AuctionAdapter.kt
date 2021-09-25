package com.example.presentation.ui.auctions.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.auction.AuctionEntityItem

class AuctionAdapter : RecyclerView.Adapter<AuctionViewHolder>() {

  private val listOfAuctions = mutableListOf<AuctionEntityItem>()

  fun setListOfAuctions(list: List<AuctionEntityItem>) {
    listOfAuctions.clear()
    listOfAuctions.addAll(list)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuctionViewHolder {
    val auctionView = AuctionItem(parent.context, null)
    return AuctionViewHolder(auctionView)
  }

  override fun onBindViewHolder(holder: AuctionViewHolder, position: Int) {
    holder.bind(listOfAuctions[position])
  }

  override fun getItemCount(): Int = listOfAuctions.size
}
