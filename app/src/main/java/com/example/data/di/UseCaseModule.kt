package com.example.data.di

import com.example.data.usecase.AuctionUseCase
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.usecase.auction.*

object UseCaseModule {

    fun provideAuctionUseCase(auctionRepo: AuctionRepository) =
        AuctionUseCase(
            GetListOfAuctions(auctionRepo),
            GetExportAuctions(auctionRepo),
            GetNumberOfSearchResults(auctionRepo)
        )
}
