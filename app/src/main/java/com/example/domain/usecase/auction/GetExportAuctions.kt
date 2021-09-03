package com.example.domain.usecase.auction

import android.util.Log
import com.example.App
import com.example.R
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.usecase.BaseUseCase
import com.example.domain.util.HandleCallbackError
import okhttp3.ResponseBody

class GetExportAuctions(
    private val auctionRepo: AuctionRepository
) : BaseUseCase<String?, String?> {

    override suspend fun execute(params: String?, callback: BaseUseCase.Callback<String?>) {
        try {
            val response = auctionRepo.exportAuctions()

            val res = response.body()?.string().toString()
            Log.d("ispsiovo", response.body()?.string().toString())
            Log.d("oisdsads", res + " sdads")
            when (response.code()) {
                200 -> response.body()?.let { result ->
                    Log.d("ispisovo1234", response.body()?.string().toString() + "dsadas")
                    Log.d("stajeovo", result.string())
                    callback.onSuccess(res)
                } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
                404 -> callback.onError(App.getStringResource(R.string.auction_not_found))
                else -> callback.onError(App.getStringResource(R.string.unexpected_error))
            }
        } catch (e: Exception) {
            Log.d("ispis", e.toString())
            HandleCallbackError<String?>().handleOnErrorCallback(e, callback)
        }
    }
}
