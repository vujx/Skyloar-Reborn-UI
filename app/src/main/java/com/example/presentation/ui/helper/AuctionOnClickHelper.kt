package com.example.presentation.ui.helper

import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel

class AuctionOnClickHelper(
    private val viewModelAuction: AuctionViewModel,
    private val page: Int,
    private val number: Int,
    private val input: String?,
    private val minPrice: Int?,
    private val maxPrice: Int?
) {

    fun onSearchBtnClickListener() {
        viewModelAuction.getListOfAuctions(page, number, input, minPrice, maxPrice)
    }
}