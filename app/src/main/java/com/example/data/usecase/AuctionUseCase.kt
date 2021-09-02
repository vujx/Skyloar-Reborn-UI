package com.example.data.usecase

import com.example.domain.usecase.auction.*

data class AuctionUseCase(
    val getListOfAuctions: GetListOfAuctions,
    val getAuctionById: GetAuctionById,
    val getCardById: GetCardById,
    val getExportAuctions: GetExportAuctions,
    val getNumberOfSearchResults: GetNumberOfSearchResults
)
