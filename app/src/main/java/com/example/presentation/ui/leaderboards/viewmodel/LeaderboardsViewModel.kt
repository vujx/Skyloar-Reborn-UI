package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.leaderboards.GetMaps
import com.example.domain.usecase.leaderboards.GetRanges
import kotlinx.coroutines.launch
import com.example.util.Result

class LeaderboardsViewModel(private val getMapsUseCase: GetMaps, private val getRanges: GetRanges) : ViewModel() {

  val listOfRanges = MutableLiveData<Map<Int, String>>()

  fun getRange(path: String) {
    viewModelScope.launch {
      when(val result = getRanges(path)) {
        is Result.Success -> listOfRanges.postValue(result.data!!)
        is Result.Error -> {

        }
      }
    }
  }

  fun getMaps(type: Int): MutableMap<Int, String> {
    var map = mutableMapOf<Int, String>()
    viewModelScope.launch {
      when(val result = getMapsUseCase(type)) {
        is Result.Success -> map = result.data
        is Result.Error -> {

        }
      }
    }
    return map
  }
}
