package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.PvPPlayer
import com.example.domain.usecase.leaderboards.pvp.GetNumOfPvPSearchResult
import com.example.domain.usecase.leaderboards.pvp.GetPvPPlayers
import com.example.presentation.ui.BaseViewModel
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result

class PvPPlayerViewModel(
  private val getPvPPlayers: GetPvPPlayers,
  private val getNumOfPvPSearchResult: GetNumOfPvPSearchResult,
) : BaseViewModel() {

  private val _pvpPlayer = MutableLiveData<Resource<List<PvPPlayer>?>>()
  val pvpPlayer: LiveData<Resource<List<PvPPlayer>?>> = _pvpPlayer

  init {
    getPvPPlayers(
      "1v1",
      0,
      1,
      20
    )
  }

  fun getPvPPlayers(
    type: String,
    month: Int,
    page: Int,
    number: Int
  ) {
    viewModelScope.launch {
      getNumOfSearchResult(1, page)
      _pvpPlayer.postValue(Resource.Loading())
      when (val result = getPvPPlayers(listOf(type, month, page, number))) {
        is Result.Success -> _pvpPlayer.postValue(Resource.Success(result.data))
        is Result.Error -> _pvpPlayer.postValue(Resource.Failure(result.error))
      }
    }
  }
}
