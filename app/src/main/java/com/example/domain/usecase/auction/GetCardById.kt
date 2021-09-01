package com.example.domain.usecase.auction

import com.example.App
import com.example.R
import com.example.data.model.auction.CardEntity
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.usecase.BaseUseCase
import com.example.domain.util.HandleCallbackError
import java.lang.Exception

class GetCardById(
    private val auctionRepo: AuctionRepository
) : BaseUseCase<Int, CardEntity> {

    override suspend fun execute(params: Int, callback: BaseUseCase.Callback<CardEntity>) {
        try {
            val response = auctionRepo.getCardById(params)

            when (response.code()) {
                200 -> response.body()?.let { result ->
                    callback.onSuccess(result)
                } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
                404 -> callback.onError(App.getStringResource(R.string.auction_not_found))
                else -> callback.onError(App.getStringResource(R.string.unexpected_error))
            }
        } catch (e: Exception) {
            HandleCallbackError<CardEntity>().handleOnErrorCallback(e, callback)
        }
    }
}
