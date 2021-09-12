package com.example.data.mapper

import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.domain.model.PvPPlayer

class PvPPlayerMapper : EntityMapper<PlayerPvPEntityItem, PvPPlayer> {

    override fun mapFromEntity(entity: PlayerPvPEntityItem): PvPPlayer =
        PvPPlayer(
            entity.activity.toInt(),
            entity.baseElo,
            entity.losesLimited,
            entity.name,
            entity.rating.toInt(),
            entity.totalMatches,
            entity.winsLimited
        )
}
