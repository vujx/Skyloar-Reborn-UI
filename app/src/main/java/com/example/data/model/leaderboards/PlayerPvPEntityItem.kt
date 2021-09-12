package com.example.data.model.leaderboards

data class PlayerPvPEntityItem(
    val activity: Double,
    val baseElo: Int,
    val losesLimited: Int,
    val name: String,
    val rating: Double,
    val totalMatches: Int,
    val winsLimited: Int
)

// players -> List<String>?
// name -> String?
