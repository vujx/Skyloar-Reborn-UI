package com.example.domain.model

data class PvPPlayer(
    val activity: Int,
    val baseElo: Int,
    val losesLimited: Int,
    val name: String,
    val rating: Int,
    val totalMatches: Int,
    val winsLimited: Int
)
