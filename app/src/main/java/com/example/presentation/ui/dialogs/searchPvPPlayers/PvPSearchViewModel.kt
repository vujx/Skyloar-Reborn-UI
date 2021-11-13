package com.example.presentation.ui.dialogs.searchPvPPlayers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.presentation.ui.dialogs.searchPvPPlayers.PvPFilterViewState.Content
import kotlinx.coroutines.launch

class PvPSearchViewModel(
  private val mapper: MapPvPPlayerData
) : ViewModel() {

  val viewState = MutableLiveData<PvPFilterViewState>()

  fun requestData(
    month: MutableMap<Int, String>,
    selectedMonth: Int,
  ) {
    viewModelScope.launch {
      viewState.value = Content(mapper.map(month, selectedMonth))
    }
  }

  fun onItemClick(item: PvPPlayerFilterUiModels) {
    (viewState.value as? Content)?.filterList?.let { filterList ->
      val newFilterList = mapper.update(filterList, item)
      viewState.postValue(Content(newFilterList))
    }
  }

  fun onSearchClick(
    month: MutableMap<Int, String>,
  ): Int {
    (viewState.value as? Content)?.filterList?.let { filterList ->
      return mapper.getSearchResult(filterList, month)
    } ?: return 0
  }
}