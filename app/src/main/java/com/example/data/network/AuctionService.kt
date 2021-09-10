package com.example.data.network

import androidx.annotation.Keep
import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.NameValueEntityItem
import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.data.model.leaderboards.TimeToNextRefreshEntity
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
    @GET("/api/stats/{statPath}")
    suspend fun getCount(
        @Path("statPath") stat: String
    ): Response<StatEntity>

    @Keep
    @GET("/api/stats/{statPath1}/{statPath2}")
    suspend fun getCountWithMultiplePath(
        @Path("statPath1") stat1: String,
        @Path("statPath2") stat2: String
    ): Response<StatEntity>

    @Keep
    @GET("/api/leaderboards/next-load")
    suspend fun getTimeToNextRefresh(): Response<TimeToNextRefreshEntity>

    @Keep
    @GET("/api/leaderboards/maps")
    suspend fun getMapsByMode(
        @Query("type") type: Int
    ): Response<List<NameValueEntityItem>>

    @Keep
    @GET("/api/leaderboards/{path}")
    suspend fun getRanges(
        @Path("path") path: String
    ): Response<List<NameValueEntityItem>>

    @Keep
    @GET("/api/leaderboards/pve-count")
    suspend fun getPvECount(
        @Query("type") type: Int,
        @Query("players") players: Int,
        @Query("map") map: Int,
        @Query("month") month: Int
    ): Response<NumberOfSearchResultsEntity>

    @Keep
    @GET("/api/leaderboards/pve")
    suspend fun getListOfPvELeaderboard(
        @Query("type") type: Int,
        @Query("players") players: Int,
        @Query("map") map: Int,
        @Query("month") month: Int,
        @Query("page") page: Int,
        @Query("number") number: Int,
    ): Response<List<PlayerPvEEntityItem>>

    @Keep
    @GET("/api/leaderboards/pvp-count")
    suspend fun getPvPCount(
        @Query("type") type: String,
        @Query("month") month: Int
    ): Response<NumberOfSearchResultsEntity>

    @Keep
    @GET("/api/leaderboards/pvp")
    suspend fun getListOfPvPLeaderboard(
        @Query("type") type: String,
        @Query("month") month: Int,
        @Query("page") page: Int,
        @Query("number") number: Int,
    ): Response<List<PlayerPvPEntityItem>>

}
