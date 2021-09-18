package com.example.presentation.ui.helper.auction

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.BR
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel

class RefreshAuctionsHelper() : BaseObservable() {

  @Bindable
  private var isLoading = false

  fun isLoading() = isLoading

  private fun setLoading() {
    isLoading = false
    notifyPropertyChanged(BR.loading)
  }

  fun refreshList(
    viewModelAuction: AuctionViewModel,
    page: String,
    number: Int,
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ) {
    val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
    viewModelAuction.getListOfAuctions(numOfPage, number, input, minPrice, maxPrice)
    setLoading()
  }
}
