package com.example.domain.usecase.auction

import android.util.Log
import com.example.App
import com.example.R
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.usecase.BaseUseCase
import com.example.domain.util.HandleCallbackError

class GetExportAuctions(
    private val auctionRepo: AuctionRepository
) : BaseUseCase<String?, Any> {

    override suspend fun execute(params: String?, callback: BaseUseCase.Callback<Any>) {
        try {
            val response = auctionRepo.exportAuctions()

            when (response.code()) {
                200 -> response.body()?.let { result ->
                    callback.onSuccess(result)
                } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
                404 -> callback.onError(App.getStringResource(R.string.auction_not_found))
                else -> callback.onError(App.getStringResource(R.string.unexpected_error))
            }
        } catch (e: Exception) {
            Log.d("ispis", e.toString())
            HandleCallbackError<Any>().handleOnErrorCallback(e, callback)
        }
    }
}
