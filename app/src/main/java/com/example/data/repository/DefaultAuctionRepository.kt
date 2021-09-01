package com.example.data.repository

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.CardEntity
import com.example.data.model.auction.ListOfAuctionsEntity
import com.example.data.network.AuctionService
import com.example.domain.repository.auction.AuctionNetworkDataSource
import retrofit2.Response

class DefaultAuctionRepository(private val auctionService: AuctionService) :
    AuctionNetworkDataSource {

    override suspend fun getListOfAuctions(
        page: Int,
        number: Int,
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ): Response<ListOfAuctionsEntity> =
        auctionService.getListOfAuctions(page, number, input, minPrice, maxPrice)

    override suspend fun getAuctionById(id: Int): Response<AuctionEntityItem> =
        auctionService.getAuctionById(id)

    override suspend fun getCardById(id: Int): Response<CardEntity> =
        auctionService.getCardById(id)

    override suspend fun exportAuctions(): Response<Any> =
        auctionService.exportAuctions()
}
