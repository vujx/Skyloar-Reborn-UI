package com.example.data.mapper

import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.domain.model.PvPPlayer1v1

class PvPPlayer1v1Mapper : EntityMapper<PlayerPvPEntityItem, PvPPlayer1v1> {

    override fun mapFromEntity(entity: PlayerPvPEntityItem): PvPPlayer1v1 =
        PvPPlayer1v1(
            entity.activity.toInt(),
            entity.baseElo,
            entity.losesLimited,
            entity.name,
            entity.rating.toInt(),
            entity.totalMatches,
            entity.winsLimited
        )
}