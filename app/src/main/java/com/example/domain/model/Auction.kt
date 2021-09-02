package com.example.domain.model

data class Auction(
    val auctionId: Int,
    val buyoutPrice: Double,
    val cardId: Int,
    val cardName: String,
    val currentPrice: Double,
    val endingOn: String,
    val startingPrice: Double
)
