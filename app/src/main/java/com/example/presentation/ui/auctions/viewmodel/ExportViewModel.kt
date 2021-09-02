package com.example.presentation.ui.auctions.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.example.R
import com.example.data.usecase.AuctionUseCase
import com.example.domain.model.Auction
import com.example.domain.usecase.BaseUseCase
import com.example.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExportViewModel(private val auctionUseCase: AuctionUseCase) :
    ViewModel(),
    BaseUseCase.Callback<Any> {

    private val _exportFile = MutableLiveData<Resource<Any>>()
    val exportFile: LiveData<Resource<Any>> = _exportFile

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        onError(App.getStringResource(R.string.unexpected_error))
    }

    fun getExportFile() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        _exportFile.postValue(Resource.Loading())
        auctionUseCase.getExportAuctions.execute(null, this@ExportViewModel)
    }

    override fun onSuccess(result: Any) {
        _exportFile.postValue(Resource.Loading())
        _exportFile.postValue(Resource.Success(result))
    }

    override fun onError(errorMessage: String) {
        _exportFile.postValue(Resource.Loading())
        _exportFile.postValue(Resource.Failure(errorMessage))
    }
}