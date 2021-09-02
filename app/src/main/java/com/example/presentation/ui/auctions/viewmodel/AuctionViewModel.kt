package com.example.presentation.ui.auctions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.example.R
import com.example.data.model.auction.AuctionEntityItem
import com.example.data.usecase.AuctionUseCase
import com.example.domain.model.Auction
import com.example.domain.usecase.BaseUseCase
import com.example.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuctionViewModel(private val auctionUseCase: AuctionUseCase) :
    ViewModel(),
    BaseUseCase.Callback<List<AuctionEntityItem>> {

    private val _auctions = MutableLiveData<Resource<List<AuctionEntityItem>>>()
    val auctions: LiveData<Resource<List<AuctionEntityItem>>> = _auctions

    val numOfSearchResult = MutableLiveData<Int>()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        onError(App.getStringResource(R.string.unexpected_error))
    }

    private fun getNumberOfSearchResults(
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ) = viewModelScope.launch(Dispatchers.IO) {
        val listOfParams = listOf<Any?>(input, minPrice, maxPrice)
        numOfSearchResult.postValue(auctionUseCase.getNumberOfSearchResults.getNumberOfSearchResult(listOfParams))
    }

    fun getListOfAuctions(
        page: Int,
        number: Int,
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ) {
        val listOfParams = listOf<Any?>(page, number, input, minPrice, maxPrice)
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _auctions.postValue(Resource.Loading())
            auctionUseCase.getListOfAuctions.execute(listOfParams, this@AuctionViewModel)
            getNumberOfSearchResults(input, minPrice, maxPrice)
        }
    }

    override fun onSuccess(result: List<AuctionEntityItem>) {
        if (result.isEmpty()) _auctions.postValue(Resource.Empty())
        else _auctions.postValue(Resource.Success(result))
    }

    override fun onError(errorMessage: String) {
        _auctions.postValue(Resource.Failure(errorMessage))
    }
}