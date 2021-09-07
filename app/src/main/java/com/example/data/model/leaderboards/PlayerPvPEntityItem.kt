package com.example.data.model.leaderboards

data class PlayerPvPEntityItem(
    val activity: Int,
    val baseElo: Int,
    val losses: Int,
    val players: List<String>,
    val rating: Int,
    val wins: Int
)