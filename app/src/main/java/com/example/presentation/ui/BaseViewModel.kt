package com.example.presentation.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.util.Result
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

  val numOfSearchResult = MutableLiveData<Int>()
  val pageResult = MutableLiveData<String>()

  fun getNumOfSearchResult(
    countSearch: Int,
    page: Int,
  ) = viewModelScope.launch {
    if (countSearch != -1) {
      numOfSearchResult.postValue(countSearch)
      val numOfPage = (countSearch.toDouble() / 20)
      when {
        countSearch == 0 || (numOfPage < 1 && countSearch != 0) -> pageResult.postValue("1 / 1")
        numOfPage % 1 == 0.0 -> pageResult.postValue("$page / ${((countSearch / 20))}")
        else -> pageResult.postValue("$page / ${((countSearch / 20) + 1)}")
      }
    } else {
      numOfSearchResult.postValue(0)
      pageResult.postValue("1 / 1")
    }
  }
}

