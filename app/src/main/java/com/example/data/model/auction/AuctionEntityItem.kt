package com.example.data.model.auction

data class AuctionEntityItem(
  val auctionId: Int,
  val buyoutPrice: Int,
  val cardId: Int,
  val cardName: String,
  val currentPrice: Int,
  val endingOn: String,
  val startingPrice: Int
)
