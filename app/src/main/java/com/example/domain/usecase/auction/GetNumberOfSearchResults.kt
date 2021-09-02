package com.example.domain.usecase.auction

import com.example.domain.repository.auction.AuctionRepository

class GetNumberOfSearchResults(
    private val auctionRepo: AuctionRepository
)  {

    suspend fun getNumberOfSearchResult(params: List<Any?>): Int {
        try {
            val response = auctionRepo.getNumberOfSearchResults(
                params[0]?.let {
                    it as String
                },
                params[1]?.let {
                    it as Int
                },
                params[2]?.let {
                    it as Int
                }
            )

            return if(response.code() == 200){
                response.body()?.count ?:  0
            } else  0
        } catch(e: Exception) {
            return 0
        }
    }
}