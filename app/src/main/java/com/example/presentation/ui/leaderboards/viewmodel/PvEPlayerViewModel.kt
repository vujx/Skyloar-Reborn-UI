package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PvEPlayer
import com.example.domain.usecase.leaderboards.pve.GetNumOfPvESearchResult
import com.example.domain.usecase.leaderboards.pvp.GetPvPPlayers
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result

class PvEPlayerViewModel(
  private val getPvPPlayersUseCase: GetPvPPlayers,
  private val getNumOfPvESearchResult: GetNumOfPvESearchResult
) :
  ViewModel() {

  private val _pvePlayer = MutableLiveData<Resource<List<PvEPlayer>?>>()
  val pvePlayer: LiveData<Resource<List<PvEPlayer>?>> = _pvePlayer

  val numOfSearchResult = MutableLiveData<Int>()
  val pageResult = MutableLiveData<String>()

  init {
    getPvEPlayers(1, 1, 9, 0, 1, 20)
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
      when (val result = getPvPPlayersUseCase(listOf(type, players, map, month, page, number))) {
        is Result.Success -> {
          result.data?.let {
            if (it.isEmpty()) _pvePlayer.postValue(Resource.Empty())
          } ?: _pvePlayer.postValue(Resource.Success(null))
        }
        is Result.Error -> {
        }
      }
    }
    getNumOfPvPSearchResult(type, players, map, month, page)
  }

  private fun getNumOfPvPSearchResult(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: Int
  ) = viewModelScope.launch {
    when (
      val result = getNumOfPvESearchResult(
        type,
        players,
        map,
        month
      )
    ) {
      is Result.Success -> {
        val countSearch = result.data.count
        val numOfPage = (countSearch.toDouble() / 20)
        numOfSearchResult.postValue(countSearch)
        when {
          countSearch == 0 -> pageResult.postValue("1 / 1")
          (countSearch.toDouble() / numOfPage) % 1 == 0.0 -> pageResult.postValue("$page / ${((countSearch / numOfPage))}")
          else -> pageResult.postValue("$page / ${((countSearch / numOfPage) + 1)}")
        }
      }
      is Result.Error -> {
      }
    }
  }
}
