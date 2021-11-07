package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PvEPlayer
import com.example.domain.usecase.leaderboards.GetMaps
import com.example.domain.usecase.leaderboards.GetRanges
import com.example.domain.usecase.leaderboards.pve.GetPvEPlayers
import com.example.presentation.ui.BaseViewModel
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result
import com.example.util.Result.Success

class PvEPlayerViewModel(
  private val getPvEPlayersUseCase: GetPvEPlayers,
  private val getMapsUseCase: GetMaps,
  private val getRanges: GetRanges,
) : BaseViewModel() {

  companion object {
    var getMapList: MutableMap<Int, String> = mutableMapOf()
    var getMonthList: MutableMap<Int, String> = mutableMapOf()
  }

  private val _pvePlayer = MutableLiveData<Resource<List<PvEPlayer>?>>()
  val pvePlayer: LiveData<Resource<List<PvEPlayer>?>> = _pvePlayer
  var pageNumber = 1
  var currentMap = 0
  var currentMonth = 0
  var currentPLayers = 0

  private fun getInitPvePlayer(type: Int, map: Int, month: Int) {
    getPvEPlayers(type, type, map, month, 1, 20)
    currentMonth = month
    currentMap = map
  }

  fun getMaps(type: Int) {
    viewModelScope.launch {
      _pvePlayer.postValue(Resource.Loading())
      when (val result = getMapsUseCase(type)) {
        is Success -> {
          getMapList = result.data
          when(val month = getRanges("ranges")) {
            is Success -> {
              getMonthList = month.data
              getInitPvePlayer(type, getMapList.keys.first(), getMonthList.keys.first())
            }
            is Result.Error -> {
              getNumOfSearchResult(-1, 1)
              _pvePlayer.postValue(Resource.Failure(month.error))
            }
          }

        }
        is Result.Error -> {
          getNumOfSearchResult(-1, 1)
          _pvePlayer.postValue(Resource.Failure(result.error))
        }
      }
    }
  }

  fun getPvEPlayers(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: Int,
    number: Int
  ) {
    viewModelScope.launch {
      _pvePlayer.postValue(Resource.Loading())
      pageNumber = page
      when (val result = getPvEPlayersUseCase(type, players, map, month, page, number)) {
        is Success -> {
          if(result.data.pvePlayers.isNullOrEmpty()) _pvePlayer.postValue(Resource.Empty())
          _pvePlayer.postValue(Resource.Success(result.data.pvePlayers))
          getNumOfSearchResult(result.data.numberOfSearchResults.count, page)
          currentMonth = month
          currentMap = map
          currentPLayers = players
        }
        is Result.Error -> {
          getNumOfSearchResult(-1, page)
          _pvePlayer.postValue(Resource.Failure(result.error))
        }
      }
    }
  }

  fun onPreviousPress(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: String,
    number: Int
  ) {
    if (page != "1 / 1") {
      if (getFirstPage(page) == 1) {
        getPvEPlayers(type, players, map, month, getLastPage(page), number)
      } else {
        getPvEPlayers(type, players, map, month,getFirstPage(page) - 1, number)
      }
    }
  }

  fun onNextPress(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: String,
    number: Int
  ) {
    if (page != "1 / 1") {
      if (getFirstPage(page) == getLastPage(page)) {
        getPvEPlayers(type, players, map, month,1, number)
      } else {
        getPvEPlayers(type, players, map, month,getFirstPage(page) + 1, number)
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
