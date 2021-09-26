package com.example.presentation.ui.auctions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.model.auction.AuctionEntityItem
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.domain.usecase.auction.GetListOfAuctions
import com.example.domain.usecase.auction.GetNumberOfSearchResults
import com.example.presentation.ui.BaseViewModel
import com.example.presentation.ui.HandleError
import com.example.util.Resource
import com.example.util.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class AuctionViewModel(
  private val getAuctions: GetListOfAuctions,
  private val getNumberOfSearchResults: GetNumberOfSearchResults,
  private val handleError: HandleError
) : BaseViewModel() {

  private var resultAuctions: Result<List<AuctionEntityItem>>? = null
  private var numberOfResult: Result<NumberOfSearchResultsEntity>? = null

  private val _auctions = MutableLiveData<Resource<List<AuctionEntityItem>>>()
  val auctions: LiveData<Resource<List<AuctionEntityItem>>> = _auctions

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
    minPrice: Int?,
    maxPrice: Int?
  ) {
    viewModelScope.launch {
      _auctions.postValue(Resource.Loading())
      listOf(
        async {
          resultAuctions = getAuctions(
            listOf<Any?>(
              page,
              number,
              input,
              minPrice,
              maxPrice,
            )
          )
        },
        async {
          numberOfResult = getNumberOfSearchResults(
            listOf<Any?>(
              input,
              minPrice,
              maxPrice,
            )
          )
        }
      ).awaitAll()

      sada()
      //sadsaddsaads
    }
  }


  private fun sada() {

  }
}
