package com.example.presentation.ui.dialogs.searchPvEPlayers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.Content
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.Loading
import kotlinx.coroutines.launch

class PvEFilterViewModel(
  private val mapper: MapPvEPlayerData
) : ViewModel() {

  val viewState = MutableLiveData<PvEFilterViewState>()

  fun requestData(
    map: MutableMap<Int, String>,
    month: MutableMap<Int, String>,
    type: Int?
  ) {
    viewState.postValue(Loading)
    viewModelScope.launch {
      viewState.postValue(Content(mapper.map(map, month, type)))
    }
  }
}