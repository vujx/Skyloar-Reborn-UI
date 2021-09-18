package com.example.domain.usecase.auction

import com.example.App
import com.example.R
import com.example.data.model.auction.AuctionEntityItem
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.usecase.BaseUseCase
import com.example.util.HandleCallbackError

class GetListOfAuctions(
  private val auctionRepo: AuctionRepository
) : BaseUseCase<List<Any?>, List<AuctionEntityItem>> {

  override suspend fun execute(
    params: List<Any?>,
    callback: BaseUseCase.Callback<List<AuctionEntityItem>>
  ) {
    try {
      val response = auctionRepo.getListOfAuctions(
        params[0] as Int,
        params[1] as Int,
        params[2]?.let {
          it as String
        },
        params[3]?.let {
          it as Int
        },
        params[4]?.let {
          it as Int
        }
      )
      when (response.code()) {
        200 -> {
          response.body()?.let { result ->
            callback.onSuccess(result)
          } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
        }
        404 -> callback.onError(App.getStringResource(R.string.auction_not_found))
        else -> callback.onError(App.getStringResource(R.string.unexpected_error))
      }
    } catch (e: Exception) {
      HandleCallbackError<List<AuctionEntityItem>>().handleOnErrorCallback(e, callback)
    }
  }
}
