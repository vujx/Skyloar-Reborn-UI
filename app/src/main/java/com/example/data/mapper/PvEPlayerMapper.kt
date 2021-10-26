package com.example.data.mapper

import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.domain.model.PvEPlayer

class PvEPlayerMapper() : EntityMapper<PlayerPvEEntityItem, PvEPlayer> {
  override fun mapFromEntity(entity: PlayerPvEEntityItem): PvEPlayer =
    PvEPlayer(
      entity.players,
      convertTime(entity.time),
      entity.difficulty,
      entity.difficultyStr,
      entity.map,
    )

  fun convertTime(time: Int): String =
    try {
      val min = time.toDouble() / 600
      val sec = (time.toDouble() / 100) % 60
      min.toString().substring(0, 2) + "m${sec.toString().substring(0, 2)}" + "s"
    } catch (e: Exception) {
      "0m"
    }
}
