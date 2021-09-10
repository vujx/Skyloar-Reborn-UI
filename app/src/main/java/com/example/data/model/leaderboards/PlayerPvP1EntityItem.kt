package com.example.data.model.leaderboards

data class PlayerPvP1EntityItem(
    val activity: Int,
    val baseElo: Int,
    val losesLimited: Int,
    val name: String,
    val rating: Int,
    val totalMatches: Int,
    val winsLimited: Int
)