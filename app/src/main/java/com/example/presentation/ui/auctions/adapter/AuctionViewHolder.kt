package com.example.presentation.ui.auctions.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.auction.AuctionEntityItem

class AuctionViewHolder(
  private val itemAuction: AuctionItem
) : RecyclerView.ViewHolder(itemAuction) {

  fun bind(auction: AuctionEntityItem, position: Int) {
    itemAuction.bind(auction, position)
  }
}
