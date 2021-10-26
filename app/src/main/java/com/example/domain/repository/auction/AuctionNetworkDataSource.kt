package com.example.domain.repository.auction

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.util.Result

interface AuctionNetworkDataSource {

  suspend fun getListOfAuctions(
    page: Int,
    number: Int,
    input: String?,
    minPrice: Long?,
    maxPrice: Long?
  ): Result<List<AuctionEntityItem>>

  suspend fun getNumberOfSearchResults(
    input: String?,
    minPrice: Long?,
    maxPrice: Long?
  ): Result<NumberOfSearchResultsEntity>
}
