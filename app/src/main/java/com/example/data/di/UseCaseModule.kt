package com.example.data.di

import com.example.data.usecase.AuctionUseCase
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.usecase.auction.GetAuctionById
import com.example.domain.usecase.auction.GetCardById
import com.example.domain.usecase.auction.GetExportAuctions
import com.example.domain.usecase.auction.GetListOfAuctions

object UseCaseModule {

    fun provideAuctionUseCase(auctionRepo: AuctionRepository) =
        AuctionUseCase(
            GetListOfAuctions(auctionRepo),
            GetAuctionById(auctionRepo),
            GetCardById(auctionRepo),
            GetExportAuctions(auctionRepo)
        )
}
