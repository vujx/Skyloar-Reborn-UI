package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PvEPlayer
import com.example.domain.usecase.leaderboards.pve.GetPvEPlayers
import com.example.presentation.ui.BaseViewModel
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result

class PvEPlayerViewModel(
  private val getPvEPlayersUseCase: GetPvEPlayers,
) : BaseViewModel() {

  private val _pvePlayer = MutableLiveData<Resource<List<PvEPlayer>?>>()
  val pvePlayer: LiveData<Resource<List<PvEPlayer>?>> = _pvePlayer
  var pageNumber = 1

  init {
    getPvEPlayers(
      1,
      1,
      67,
      0,
      1,
      20
    )
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
      when (val result = getPvEPlayersUseCase(listOf(type, players, map, month, page, number))) {
        is Result.Success -> {
          if(result.data.pvePlayers.isNullOrEmpty()) _pvePlayer.postValue(Resource.Empty())
          _pvePlayer.postValue(Resource.Success(result.data.pvePlayers))
          getNumOfSearchResult(result.data.numberOfSearchResults.count, page)
        }
        is Result.Error -> {
          getNumOfSearchResult(-1, page)
          _pvePlayer.postValue(Resource.Failure(result.error))
        }
      }
    }
  }
}
