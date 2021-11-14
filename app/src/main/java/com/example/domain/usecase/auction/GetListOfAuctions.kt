package com.example.domain.usecase.auction

import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.domain.error.ErrorEntity
import com.example.domain.repository.auction.AuctionRepository
import com.example.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class GetListOfAuctions(
  private val auctionRepo: AuctionRepository,
) {

  private var resultAuctions: Result<List<AuctionEntityItem>>? = null
  private var numberOfResult: Result<NumberOfSearchResultsEntity>? = null

  suspend operator fun invoke(params: List<Any?>): Result<AuctionsData> =
    withContext(Dispatchers.IO) {
      withTimeoutOrNull(2000) {
        listOf(
          async {
            resultAuctions = auctionRepo.getListOfAuctions(
              params[0] as Int,
              params[1] as Int,
              params[2]?.let {
                it as String
              },
              params[3]?.let {
                it as Long
              },
              params[4]?.let {
                it as Long
              }
            )
          },
          async {
            numberOfResult = auctionRepo.getNumberOfSearchResults(
              params[2]?.let {
                it as String
              },
              params[3]?.let {
                it as Long
              },
              params[4]?.let {
                it as Long
              }
            )
          }
        ).awaitAll()
      } ?: return@withContext Result.Error(ErrorEntity.ServiceUnavailable)

      return@withContext if (resultAuctions is Result.Success && numberOfResult is Result.Success) {
        val auctionsData = AuctionsData(
          (resultAuctions as Result.Success<List<AuctionEntityItem>>).data,
          (numberOfResult as Result.Success<NumberOfSearchResultsEntity>).data,
        )
        Result.Success(auctionsData)
      } else if (resultAuctions is Result.Error) Result.Error((resultAuctions as Result.Error).error)
       else Result.Error((numberOfResult as Result.Error).error)
    }
}

data class AuctionsData(
  val auctions: List<AuctionEntityItem>,
  val numberOfSearch: NumberOfSearchResultsEntity,
)
