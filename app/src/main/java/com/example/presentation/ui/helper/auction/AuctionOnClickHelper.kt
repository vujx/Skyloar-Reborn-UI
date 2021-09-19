package com.example.presentation.ui.helper.auction

import com.example.presentation.ui.auctions.AuctionFragment
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.presentation.ui.dialogs.DialogForAddingPageNumber
import com.example.util.Constants
import com.example.util.exportFile

class AuctionOnClickHelper() {

  fun onBackBtnPress(
    viewModelAuction: AuctionViewModel,
    page: String,
    number: Int,
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ) {
    val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
    val lastPage = page.substring(numOfPage.toString().length + 3).toInt()
    if (numOfPage == 1)
      viewModelAuction.getListOfAuctions(lastPage, number, input, minPrice, maxPrice)
    else
      viewModelAuction.getListOfAuctions(numOfPage - 1, number, input, minPrice, maxPrice)
  }

  fun onNextBtnPress(
    viewModelAuction: AuctionViewModel,
    page: String,
    number: Int,
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ) {
    val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
    val lastPage = page.substring(numOfPage.toString().length + 3).toInt()

    if (numOfPage == lastPage)
      viewModelAuction.getListOfAuctions(1, number, input, minPrice, maxPrice)
    else
      viewModelAuction.getListOfAuctions(numOfPage + 1, number, input, minPrice, maxPrice)
  }
}
