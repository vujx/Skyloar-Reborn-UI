package com.example.presentation.ui.dialogs.searchPvPPlayers


sealed class PvPFilterViewState {

  data class Content(val filterList: List<PvPPlayerFilterUiModels>) : PvPFilterViewState()
}