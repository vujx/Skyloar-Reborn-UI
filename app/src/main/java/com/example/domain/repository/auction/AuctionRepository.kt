package com.example.domain.repository.auction

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.CardEntity
import com.example.data.model.auction.ListOfAuctionsEntity
import retrofit2.Response

class AuctionRepository(private val auctionDataSource: AuctionNetworkDataSource) {

    suspend fun getListOfAuctions(
        page: Int,
        number: Int,
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ): Response<ListOfAuctionsEntity> =
        auctionDataSource.getListOfAuctions(page, number, input, minPrice, maxPrice)

    suspend fun getAuctionById(id: Int): Response<AuctionEntityItem> =
        auctionDataSource.getAuctionById(id)

    suspend fun getCardById(id: Int): Response<CardEntity> =
        auctionDataSource.getCardById(id)

    suspend fun exportAuctions(): Response<Any> =
        auctionDataSource.exportAuctions()
}
