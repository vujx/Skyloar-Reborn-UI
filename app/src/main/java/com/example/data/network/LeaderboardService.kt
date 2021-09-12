package com.example.data.network

import androidx.annotation.Keep
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.data.model.leaderboards.NameValueEntityItem
import com.example.data.model.leaderboards.PlayerPvEEntityItem
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LeaderboardService {

    @Keep
    @GET("/api/leaderboards/maps")
    suspend fun getMapsByType(
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
    suspend fun getListOfPvEPlayers(
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
    suspend fun getListOfPvPPlayers(
        @Query("type") type: String,
        @Query("month") month: Int,
        @Query("page") page: Int,
        @Query("number") number: Int,
    ): Response<List<PlayerPvPEntityItem>>
}
