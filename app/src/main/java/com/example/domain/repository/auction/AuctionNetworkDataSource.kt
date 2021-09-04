package com.example.domain.repository.auction

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.CardEntity
import com.example.data.model.auction.NumberOfSearchResultsEntity
import okhttp3.ResponseBody
import retrofit2.Response

interface AuctionNetworkDataSource {

    suspend fun getListOfAuctions(
        page: Int,
        number: Int,
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ): Response<List<AuctionEntityItem>>

    suspend fun exportAuctions(): Response<ResponseBody>

    suspend fun getNumberOfSearchResults(
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ): Response<NumberOfSearchResultsEntity>
}
