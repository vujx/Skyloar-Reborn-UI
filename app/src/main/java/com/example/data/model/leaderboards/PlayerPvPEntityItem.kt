package com.example.data.model.leaderboards

data class PlayerPvPEntityItem(
  val players: List<String>,
  val baseElo: Long,
  val rating: Double,
  val activity: Double,
  val wins: Long,
  val losses: Long,
  val matches: Long?,
)
