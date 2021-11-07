package com.example.data.mapper

import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.domain.model.PvPPlayer

class PvPPlayerMapper : EntityMapper<PlayerPvPEntityItem, PvPPlayer> {

  override fun mapFromEntity(entity: PlayerPvPEntityItem): PvPPlayer =
    PvPPlayer(
      entity.players,
      entity.baseElo,
      entity.rating.toLong(),
      entity.activity.toLong(),
      entity.wins,
      entity.losses,
      entity.matches,
    )
}
