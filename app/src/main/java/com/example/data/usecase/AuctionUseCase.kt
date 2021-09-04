package com.example.data.usecase

import com.example.domain.usecase.auction.*

data class AuctionUseCase(
    val getListOfAuctions: GetListOfAuctions,
    val getExportAuctions: GetExportAuctions,
    val getNumberOfSearchResults: GetNumberOfSearchResults
)
