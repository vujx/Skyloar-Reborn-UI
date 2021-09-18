package com.example.data.model.leaderboards

data class PlayerPvPEntityItem(
  val activity: Double,
  val baseElo: Int,
  val losesLimited: Int?,
  val name: String?,
  val rating: Double,
  val totalMatches: Int?,
  val winsLimited: Int?,
  val players: List<String>?,
  val wins: Int?,
  val losses: Int?
)
