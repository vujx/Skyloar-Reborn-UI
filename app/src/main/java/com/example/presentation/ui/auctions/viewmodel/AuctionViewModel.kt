package com.example.presentation.ui.auctions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.auction.AuctionEntityItem
import com.example.domain.usecase.auction.GetListOfAuctions
import com.example.domain.usecase.auction.GetNumberOfSearchResults
import com.example.util.Resource
import com.example.util.Result
import kotlinx.coroutines.launch

class AuctionViewModel(
  private val getAuctions: GetListOfAuctions,
  private val getNumberOfSearchResults: GetNumberOfSearchResults
) :
  ViewModel() {

  private val _auctions = MutableLiveData<Resource<List<AuctionEntityItem>>>()
  val auctions: LiveData<Resource<List<AuctionEntityItem>>> = _auctions

  val numOfSearchResult = MutableLiveData<Int>()
  val pageResult = MutableLiveData<String>()

  init {
    getListOfAuctions(1, 20, null, null, null)
  }
  private fun getNumberOfSearchResults(
    page: Int,
    number: Int,
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ) = viewModelScope.launch {
    val listOfParams = listOf<Any?>(input, minPrice, maxPrice)
    when (val result = getNumberOfSearchResults.invoke(listOfParams)) {
      is Result.Success -> {
        val countSearch = result.data.count
        numOfSearchResult.postValue(countSearch)
        when {
          countSearch == 0 -> pageResult.postValue("1 / 1")
          (countSearch.toDouble() / number) % 1 == 0.0 -> pageResult.postValue("$page / ${((countSearch / number))}")
          else -> pageResult.postValue("$page / ${((countSearch / number) + 1)}")
        }
      }
      is Result.Error -> {
      }
    }
  }

  fun getListOfAuctions(
    page: Int,
    number: Int,
    input: String?,
    minPrice: Int?,
    maxPrice: Int?
  ) {
    viewModelScope.launch {
      _auctions.postValue(Resource.Loading())
      when (
        val result = getAuctions(
          listOf<Any?>(
            page,
            number,
            input,
            minPrice,
            maxPrice
          )
        )
      ) {
        is Result.Success -> {
          if (result.data.isEmpty()) _auctions.postValue(Resource.Empty()) else _auctions.postValue(Resource.Success(result.data))
        }
        is Result.Error -> {
        }
      }
    }
    getNumberOfSearchResults(page, number, input, minPrice, maxPrice)
  }
}
