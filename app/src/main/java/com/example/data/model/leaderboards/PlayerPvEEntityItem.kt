package com.example.data.model.leaderboards

data class PlayerPvEEntityItem(
  val players: List<String>,
  val time: Int,
  val difficulty: Int,
  val difficultyStr: String,
  val map: Int,
)
