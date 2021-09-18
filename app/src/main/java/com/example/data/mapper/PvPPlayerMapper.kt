package com.example.data.mapper

import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.domain.model.PvPPlayer

class PvPPlayerMapper : EntityMapper<PlayerPvPEntityItem, PvPPlayer> {

  override fun mapFromEntity(entity: PlayerPvPEntityItem): PvPPlayer =
    PvPPlayer(
      entity.activity.toInt(),
      entity.baseElo,
      checkWins(entity.losesLimited, entity.losses),
      checkName(entity, entity.name),
      entity.rating.toInt(),
      entity.totalMatches,
      checkWins(entity.winsLimited, entity.wins)
    )

  private fun checkName(entity: PlayerPvPEntityItem, name: String?): String {
    var nameResult = ""
    return if (name == null) {
      entity.players?.let { result ->
        for (i: Int in result.indices) {
          nameResult += if (i + 1 == result.size)
            result[i]
          else "${result[i]},\n"
        }
      }
      nameResult
    } else
      name
  }

  private fun checkWins(winsLimited: Int?, wins: Int?): Int =
    if (wins == null && winsLimited != null)
      winsLimited
    else wins ?: 0
}
