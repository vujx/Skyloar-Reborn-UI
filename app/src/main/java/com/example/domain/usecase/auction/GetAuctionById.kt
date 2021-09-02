package com.example.domain.usecase.auction

import com.example.App
import com.example.R
import com.example.data.mapper.AuctionMapper
import com.example.domain.model.Auction
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.usecase.BaseUseCase
import com.example.domain.util.HandleCallbackError
import java.lang.Exception

class GetAuctionById(
    private val auctionRepo: AuctionRepository
) : BaseUseCase<Int, Auction> {

    private val auctionMapper = AuctionMapper()

    override suspend fun execute(params: Int, callback: BaseUseCase.Callback<Auction>) {
        try {
            val response = auctionRepo.getAuctionById(params)

            when (response.code()) {
                200 -> {
                    response.body()?.let { result ->
                        callback.onSuccess(auctionMapper.mapFromEntity(result))
                    } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
                }
                404 -> callback.onError(App.getStringResource(R.string.auction_not_found))
                else -> callback.onError(App.getStringResource(R.string.unexpected_error))
            }
        } catch (e: Exception) {
            HandleCallbackError<Auction>().handleOnErrorCallback(e, callback)
        }
    }
}
