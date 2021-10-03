package com.example.data.repository.leaderboards

import android.os.Build.VERSION_CODES
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.data.network.HomeService
import com.example.data.network.NetworkResponseHelper
import com.example.domain.repository.home.HomeNetworkDataSource
import com.example.util.Result

class DefaultHomeImpl(
  private val homeService: HomeService,
  private val networkResponseHelper: NetworkResponseHelper,
) : HomeNetworkDataSource {

  @RequiresApi(VERSION_CODES.N)
  override suspend fun getIntroHomeTex(): Result<String> {
    return networkResponseHelper.safeApiCall(
      {
        Log.d("jelude", "sadsad")
        val response = homeService.getHomeText()
        Log.d("ipsi", response.isSuccessful.toString())
        Log.d("ispis", response.body().toString())
        response.body()!!
      }
    )
  }
}
