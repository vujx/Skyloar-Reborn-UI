package com.example.data.network

import androidx.annotation.Keep
import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.stat.StatEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuctionService {

    @Keep
    @GET("/api/auctions")
    suspend fun getListOfAuctions(
        @Query("page") page: Int,
        @Query("number") number: Int,
        @Query("input") input: String?,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?
    ): Response<List<AuctionEntityItem>>

    @Keep
    @GET("/api/auctions/count")
    suspend fun getNumberOfSearchResults(
        @Query("input") input: String?,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?
    ): Response<NumberOfSearchResultsEntity>

    @Keep
    @GET("/api/{statPath}")
    suspend fun getCount(
        @Path("statPath") stat: String
    ): Response<StatEntity>
}
