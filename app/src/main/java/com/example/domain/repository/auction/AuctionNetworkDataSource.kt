package com.example.domain.repository.auction

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.CardEntity
import com.example.data.model.auction.ListOfAuctionsEntity
import retrofit2.Response

interface AuctionNetworkDataSource {

    suspend fun getListOfAuctions(
        page: Int,
        number: Int,
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ): Response<ListOfAuctionsEntity>

    suspend fun getAuctionById(id: Int): Response<AuctionEntityItem>

    suspend fun getCardById(id: Int): Response<CardEntity>

    suspend fun exportAuctions(): Response<Any>
}
