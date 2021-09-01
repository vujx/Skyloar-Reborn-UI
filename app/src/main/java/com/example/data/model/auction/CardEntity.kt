package com.example.data.model.auction

data class CardEntity(
    val affinity: String,
    val cardId: Int,
    val cardName: String,
    val cardType: String,
    val expansion: String,
    val fireOrbs: Int,
    val frostOrbs: Int,
    val natureOrbs: Int,
    val neutralOrbs: Int,
    val obtainable: String,
    val promo: String,
    val rarity: String,
    val shadowOrbs: Int
)
