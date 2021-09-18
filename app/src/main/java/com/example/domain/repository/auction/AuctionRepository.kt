package com.example.domain.repository.auction

class AuctionRepository(private val auctionDataSource: AuctionNetworkDataSource) {

  suspend fun getListOfAuctions(
    page: Int,
    number: Int,
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ) = auctionDataSource.getListOfAuctions(page, number, input, minPrice, maxPrice)

  suspend fun getNumberOfSearchResults(
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ) = auctionDataSource.getNumberOfSearchResults(input, minPrice, maxPrice)
}
