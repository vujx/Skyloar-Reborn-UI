package com.example.presentation.ui.dialogs.searchPvEPlayers

sealed class PvEFilterViewState {

  data class Content(val filterList: List<PvEPlayerFilterUiModels>) : PvEFilterViewState()
}

data class PvESearchResult(
  val type: Int,
  val month: Int,
  val map: Int,
)