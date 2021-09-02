package com.example.data.network

import androidx.annotation.Keep
import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.CardEntity
import com.example.data.model.auction.ListOfAuctionsEntity
import com.example.data.model.auction.NumberOfSearchResultsEntity
import retrofit2.Response
import retrofit2.http.GET
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
    @GET("/auction")
    suspend fun getAuctionById(
        @Query("id") id: Int
    ): Response<AuctionEntityItem>

    @Keep
    @GET("/api/auctions/cards")
    suspend fun getCardById(
        @Query("id") id: Int
    ): Response<CardEntity>

    @Keep
    @GET("/api/auctions/export")
    suspend fun exportAuctions(): Response<Any>

    @Keep
    @GET("/api/auctions/count")
    suspend fun getNumberOfSearchResults(
        @Query("input") input: String?,
        @Query("minPrice") minPrice: Int?,
        @Query("maxPrice") maxPrice: Int?
    ): Response<NumberOfSearchResultsEntity>
}
