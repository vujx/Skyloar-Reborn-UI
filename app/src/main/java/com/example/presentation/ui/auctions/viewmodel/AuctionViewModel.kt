package com.example.presentation.ui.auctions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.model.auction.AuctionEntityItem
import com.example.domain.usecase.auction.GetListOfAuctions
import com.example.presentation.ui.BaseViewModel
import com.example.util.Resource
import com.example.util.Result
import kotlinx.coroutines.launch

class AuctionViewModel(
  private val getAuctions: GetListOfAuctions,
) : BaseViewModel() {

  private val _auctions = MutableLiveData<Resource<List<AuctionEntityItem>>>()
  val auctions: LiveData<Resource<List<AuctionEntityItem>>> = _auctions
  var pageNumber = 1
  init {
    getListOfAuctions(
      1,
      20,
      null,
      null,
      null,
    )
  }

  fun getListOfAuctions(
    page: Int,
    number: Int,
    input: String?,
    minPrice: Long?,
    maxPrice: Long?,
  ) {
    viewModelScope.launch {
      _auctions.postValue(Resource.Loading())
      val result = getAuctions(
        listOf<Any?>(
          page,
          number,
          input,
          minPrice,
          maxPrice,
        )
      )
      pageNumber = page
      when (result) {
        is Result.Success -> {
          if (result.data.auctions.isEmpty()) _auctions.postValue(Resource.Empty())
          else _auctions.postValue(Resource.Success(result.data.auctions))
          getNumOfSearchResult(result.data.numberOfSearch.count, page)
        }
        is Result.Error -> {
          getNumOfSearchResult(-1, page)
          _auctions.postValue(Resource.Failure(result.error))
        }
      }
    }
  }

  fun onPreviousPress(
    page: String,
    number: Int,
    input: String?,
    minPrice: Long?,
    maxPrice: Long?,
  ) {
    if (page != "1 / 1") {
      if (getFirstPage(page) == 1) {
        getListOfAuctions(getLastPage(page), number, input, minPrice, maxPrice)
      } else {
        getListOfAuctions(getFirstPage(page) - 1, number, input, minPrice, maxPrice)
      }
    }
  }

  fun onNextPress(
    page: String,
    number: Int,
    input: String?,
    minPrice: Long?,
    maxPrice: Long?,
  ) {
    if (page != "1 / 1") {
      if (getFirstPage(page) == getLastPage(page)) {
        getListOfAuctions(1, number, input, minPrice, maxPrice)
      } else getListOfAuctions(getFirstPage(page) + 1, number, input, minPrice, maxPrice)
    }
  }

  private fun getFirstPage(page: String): Int {
    return page.substring(0, page.indexOf(' ')).toInt()
  }

  private fun getLastPage(page: String): Int {
    return page.substring(getFirstPage(page).toString().length + 3).toInt()
  }
}
