package com.example.data.usecase

import com.example.domain.usecase.auction.GetListOfAuctions
import com.example.domain.usecase.auction.GetNumberOfSearchResults

data class AuctionUseCase(
  val getListOfAuctions: GetListOfAuctions,
  val getNumberOfSearchResults: GetNumberOfSearchResults
)
