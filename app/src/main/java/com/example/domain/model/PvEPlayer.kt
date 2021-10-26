package com.example.domain.model

data class PvEPlayer(
  val players: List<String>,
  val time: String,
  val difficulty: Int,
  val difficultyStr: String,
  val map: Int,
)
