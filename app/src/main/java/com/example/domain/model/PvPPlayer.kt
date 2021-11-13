package com.example.domain.model

data class PvPPlayer(
  val players: List<String>,
  val baseElo: Long,
  val rating: Long,
  val activity: Long,
  val winsLimited: Long,
  val losesLimited: Long,
  val totalMatches: Long?,
)
