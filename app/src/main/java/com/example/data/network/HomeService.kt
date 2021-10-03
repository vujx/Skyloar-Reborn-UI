package com.example.data.network

import androidx.annotation.Keep
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface HomeService {

  @Keep
  @GET("/api/info/home")
  @Headers("Content-Type: application/json")
  suspend fun getHomeText(): Response<String>
}