package com.example.data.repository

import com.example.data.model.stat.StatEntity
import com.example.data.network.AuctionService
import com.example.domain.repository.stat.StatNetworkDataSource
import retrofit2.Response

class DefaultStatRepository(private val auctionService: AuctionService) :
    StatNetworkDataSource {

    override suspend fun getCount(path: String): Response<StatEntity> =
        auctionService.getCount(path)

}