package com.example.domain.usecase.auction

import com.example.domain.repository.auction.AuctionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetListOfAuctions(
  private val auctionRepo: AuctionRepository
)  {

  suspend operator fun invoke(params: List<Any?>) =
    withContext(Dispatchers.IO) {
      auctionRepo.getListOfAuctions(params[0] as Int,
        params[1] as Int,
        params[2]?.let {
          it as String
        },
        params[3]?.let {
          it as Int
        },
        params[4]?.let {
          it as Int
        })
    }
}
