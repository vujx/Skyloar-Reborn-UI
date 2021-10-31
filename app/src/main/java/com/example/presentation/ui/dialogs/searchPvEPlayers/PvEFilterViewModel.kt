package com.example.presentation.ui.dialogs.searchPvEPlayers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.Content
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.Loading
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.NavigateToPvEFragment
import kotlinx.coroutines.launch

class PvEFilterViewModel(
  private val mapper: MapPvEPlayerData
) : ViewModel() {

  val viewState = MutableLiveData<PvEFilterViewState>()

  fun requestData(
    map: MutableMap<Int, String>,
    month: MutableMap<Int, String>,
    type: Int,
    realType: Int,
    selectedMonth: Int,
    selectedMap: Int,
  ) {
    viewState.postValue(Loading)
    viewModelScope.launch {
      viewState.postValue(Content(mapper.map(map, month, type, realType, selectedMap, selectedMonth)))
    }
  }

  fun onItemClick(item: PvEPlayerFilterUiModels) {
    (viewState.value as? Content)?.filterList?.let { filterList ->
      val newFilterList = mapper.update(filterList, item)
      viewState.postValue(Content(newFilterList))
    }
  }

  fun onSearchClick(
    map: MutableMap<Int, String>,
    month: MutableMap<Int, String>,
  ) {
    (viewState.value as? Content)?.filterList?.let { filterList ->
      viewState.postValue(NavigateToPvEFragment(mapper.getSearchResult(filterList, month, map)))
    }
  }
}