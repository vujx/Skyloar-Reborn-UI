package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PvPPlayer
import com.example.domain.usecase.leaderboards.pvp.GetNumOfPvPSearchResult
import com.example.domain.usecase.leaderboards.pvp.GetPvPPlayers
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result

class PvPPlayerViewModel(private val getPvPPlayers: GetPvPPlayers, private val getNumOfPvPSearchResult: GetNumOfPvPSearchResult) :
  ViewModel() {

  private val _pvpPlayer = MutableLiveData<Resource<List<PvPPlayer>?>>()
  val pvpPlayer: LiveData<Resource<List<PvPPlayer>?>> = _pvpPlayer

  val numOfSearchResult = MutableLiveData<Int>()
  val pageResult = MutableLiveData<String>()

  init {
    getPvPPlayers("1v1", 0, 1, 20)
  }

  fun getPvPPlayers(
    type: String,
    month: Int,
    page: Int,
    number: Int
  ) {
    viewModelScope.launch {
      _pvpPlayer.postValue(Resource.Loading())
      when (val result = getPvPPlayers(listOf(type, month, page, number))) {
        is Result.Success -> _pvpPlayer.postValue(Resource.Success(result.data))
        is Result.Error -> {
        }
      }
    }

    getNumOfPvPSearchResult(type, month, page)
  }

  private fun getNumOfPvPSearchResult(
    type: String,
    month: Int,
    page: Int
  ) = viewModelScope.launch {
    when (val result = getNumOfPvPSearchResult(type, month)) {
      is Result.Success -> {
        val countSearch = result.data.count
        numOfSearchResult.postValue(countSearch)
        val numOfPage = (countSearch.toDouble() / 20)
        when {
          countSearch == 0 || (numOfPage < 1 && countSearch != 0) -> pageResult.postValue("1 / 1")
          (countSearch.toDouble() / numOfPage) % 1 == 0.0 -> pageResult.postValue("$page / ${((countSearch / 20))}")
          else -> pageResult.postValue("$page / ${((countSearch / 20) + 1)}")
        }
      }
      is Result.Error -> {
      }
    }
  }
}
