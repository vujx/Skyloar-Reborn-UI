package com.example.data.repository.auction

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.network.AuctionStatService
import com.example.data.network.safeApiCall
import com.example.domain.repository.auction.AuctionNetworkDataSource
import com.example.util.Result

class DefaultAuctionRepository(private val auctionService: AuctionStatService) :
  AuctionNetworkDataSource {

  override suspend fun getListOfAuctions(
    page: Int,
    number: Int,
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ): Result<List<AuctionEntityItem>> {
    return safeApiCall(
      {
        auctionService.getListOfAuctions(page, number, input, minPrice, maxPrice).body()!!
      }
    )
  }

  override suspend fun getNumberOfSearchResults(
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ): Result<NumberOfSearchResultsEntity> {
    return safeApiCall(
      {
        auctionService.getNumberOfSearchResults(input, minPrice, maxPrice).body()!!
      }
    )
  }


}
