package com.example.data.repository.leaderboards

import com.example.data.network.HomeService
import com.example.data.network.NetworkResponseHelper
import com.example.domain.repository.home.HomeNetworkDataSource
import com.example.util.Result

class DefaultHomeImpl(
  private val homeService: HomeService,
  private val networkResponseHelper: NetworkResponseHelper,
) : HomeNetworkDataSource {

  override suspend fun getIntroHomeTex(): Result<String> {
    return networkResponseHelper.safeApiCall(
      {
        val response = homeService.getHomeText()
        response.body()!!
      }
    )
  }
}
