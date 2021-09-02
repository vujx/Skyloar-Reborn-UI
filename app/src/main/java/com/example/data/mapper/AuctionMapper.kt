package com.example.data.mapper

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.ListOfAuctionsEntity
import com.example.domain.model.Auction

class AuctionMapper : EntityMapper<AuctionEntityItem, Auction>{

    override fun mapFromEntity(entity: AuctionEntityItem): Auction =
        Auction(
            entity.auctionId,
            castToDouble(entity.buyoutPrice),
            entity.cardId,
            entity.cardName,
            castToDouble(entity.currentPrice),
            entity.endingOn,
            castToDouble(entity.startingPrice)
        )

    fun mapToListFromEntity(entity: ListOfAuctionsEntity) =
        entity.map { mapFromEntity(it) }

    private fun castToDouble(input: Int) = (input/1000).toDouble()
}