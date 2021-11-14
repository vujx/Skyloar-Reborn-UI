package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PvPPlayer
import com.example.domain.usecase.leaderboards.GetRanges
import com.example.domain.usecase.leaderboards.pvp.GetPvPPlayers
import com.example.presentation.ui.BaseViewModel
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result.Error
import com.example.util.Result.Success

class PvPPlayerViewModel(
  private val getPvPPlayers: GetPvPPlayers,
  private val getRanges: GetRanges,
) : BaseViewModel() {

  companion object {
    var getMonthList: MutableMap<Int, String> = mutableMapOf()
  }

  private val _pvpPlayer = MutableLiveData<Resource<List<PvPPlayer>?>>()
  val pvpPlayer: LiveData<Resource<List<PvPPlayer>?>> = _pvpPlayer

  var pageNumber = 1
  var currentMonth = -1

  private fun getInitPvPPlayer(type: String, month: Int) {
    getPvPPlayers(
      type,
      month,
      1,
      20
    )
    currentMonth = month
  }

  fun getMonth(type: String) {
    viewModelScope.launch {
      _pvpPlayer.postValue(Resource.Loading())
      when (val result = getRanges("ranges")) {
        is Success -> {
          getMonthList = result.data
          getInitPvPPlayer(type, getMonthList.keys.first())
        }
        is Error -> {
          getNumOfSearchResult(-1, 1)
          _pvpPlayer.postValue(Resource.Failure(result.error))
        }
      }
    }
  }
  fun getPvPPlayers(
    type: String,
    month: Int,
    page: Int,
    number: Int
  ) {
    viewModelScope.launch {
      _pvpPlayer.postValue(Resource.Loading())
      pageNumber = page
      when (val result = getPvPPlayers(listOf(type, month, page, number))) {
        is Success -> {
          if (result.data.pvpPlayers.isNullOrEmpty()) _pvpPlayer.postValue(Resource.Empty())
          _pvpPlayer.postValue(Resource.Success(result.data.pvpPlayers))
          getNumOfSearchResult(result.data.numberOfSearchResultsEntity.count, page)
          currentMonth = month
        }
        is Error -> {
          getNumOfSearchResult(-1, page)
          _pvpPlayer.postValue(Resource.Failure(result.error))
        }
      }
    }
  }

  fun onPreviousPress(
    type: String,
    month: Int,
    page: String,
    number: Int
  ) {
    if (page != "1 / 1") {
      if (getFirstPage(page) == 1) {
        getPvPPlayers(type, month, getLastPage(page), number)
      } else {
        getPvPPlayers(type, month, getFirstPage(page) - 1, number)
      }
    }
  }

  fun onNextPress(
    type: String,
    month: Int,
    page: String,
    number: Int
  ) {
    if (page != "1 / 1") {
      if (getFirstPage(page) == getLastPage(page)) {
        getPvPPlayers(type, month, 1, number)
      } else {
        getPvPPlayers(type, month, getFirstPage(page) + 1, number)
      }
    }
  }

  private fun getFirstPage(page: String): Int {
    return page.substring(0, page.indexOf(' ')).toInt()
  }

  private fun getLastPage(page: String): Int {
    return page.substring(getFirstPage(page).toString().length + 3).toInt()
  }
}
