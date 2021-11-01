package com.example.domain.usecase.auction

import com.example.domain.repository.auction.AuctionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNumberOfSearchResults(
  private val auctionRepo: AuctionRepository
) {

  suspend operator fun invoke(params: List<Any?>) =
    withContext(Dispatchers.IO) {
      auctionRepo.getNumberOfSearchResults(
        params[0]?.let {
          it as String
        },
        params[1]?.let {
          it as Long
        },
        params[2]?.let {
          it as Long
        }
      )
    }
}
