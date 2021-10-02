package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PvEPlayer
import com.example.domain.usecase.leaderboards.pve.GetNumOfPvESearchResult
import com.example.domain.usecase.leaderboards.pve.GetPvEPlayers
import com.example.presentation.ui.BaseViewModel
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result

class PvEPlayerViewModel(
  private val getPvEPlayersUseCase: GetPvEPlayers,
  private val getNumOfPvESearchResult: GetNumOfPvESearchResult,
) : BaseViewModel() {

  private val _pvePlayer = MutableLiveData<Resource<List<PvEPlayer>?>>()
  val pvePlayer: LiveData<Resource<List<PvEPlayer>?>> = _pvePlayer

  init {
    getPvEPlayers(
      1,
      1,
      9,
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
      when (val result = getPvEPlayersUseCase(listOf(type, players, map, month, page, number))) {
        is Result.Success -> {
          result.data?.let {
            if (it.isEmpty()) _pvePlayer.postValue(Resource.Empty())
          } ?: _pvePlayer.postValue(Resource.Success(null))
        }
        is Result.Error -> _pvePlayer.postValue(Resource.Failure(result.error))
      }
      getNumOfSearchResult(
        1,
        page
      )
    }
  }
}
