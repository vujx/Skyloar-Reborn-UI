package com.example.data.di

import com.example.data.usecase.AuctionUseCase
import com.example.data.usecase.StatUseCase
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.repository.stat.StatRepository
import com.example.domain.usecase.auction.*
import com.example.domain.usecase.stat.GetStatValues

object UseCaseModule {

    fun provideAuctionUseCase(auctionRepo: AuctionRepository) =
        AuctionUseCase(
            GetListOfAuctions(auctionRepo),
            GetNumberOfSearchResults(auctionRepo)
        )

    fun provideStatUseCase(statRepo: StatRepository) =
        StatUseCase(
            GetStatValues(statRepo)
        )
}
