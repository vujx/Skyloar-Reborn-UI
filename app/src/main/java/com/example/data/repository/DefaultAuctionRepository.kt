package com.example.data.repository

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.NumberOfSearchResultsEntity
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
    ): Response<List<AuctionEntityItem>> =
        auctionService.getListOfAuctions(page, number, input, minPrice, maxPrice)

    override suspend fun getNumberOfSearchResults(
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ): Response<NumberOfSearchResultsEntity> =
        auctionService.getNumberOfSearchResults(input, minPrice, maxPrice)
}
