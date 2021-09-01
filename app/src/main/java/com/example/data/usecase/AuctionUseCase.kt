package com.example.data.usecase

import com.example.domain.usecase.auction.GetAuctionById
import com.example.domain.usecase.auction.GetCardById
import com.example.domain.usecase.auction.GetExportAuctions
import com.example.domain.usecase.auction.GetListOfAuctions

data class AuctionUseCase(
    val getListOfAuctions: GetListOfAuctions,
    val getAuctionById: GetAuctionById,
    val getCardById: GetCardById,
    val getExportAuctions: GetExportAuctions
)
