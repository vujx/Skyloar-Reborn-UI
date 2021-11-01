package com.example.data.mapper

import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.domain.model.PvEPlayer
import kotlin.math.floor

class PvEPlayerMapper() : EntityMapper<PlayerPvEEntityItem, PvEPlayer> {
  override fun mapFromEntity(entity: PlayerPvEEntityItem): PvEPlayer =
    PvEPlayer(
      entity.players,
      convertTime(entity.time),
      entity.difficulty,
      entity.difficultyStr,
      entity.map,
    )

  private fun convertTime(time: Int): String =
    try {
      val min = floor(time.toDouble() / 10 / 60).toInt()
      val sec = (floor(time.toDouble() / 10) % 60).toInt()
      "${min}m${sec}s"
    } catch (e: Exception) {
      "0m"
    }
}
