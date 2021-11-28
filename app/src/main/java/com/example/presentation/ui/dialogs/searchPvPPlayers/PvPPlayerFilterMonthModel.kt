package com.example.presentation.ui.dialogs.searchPvPPlayers

interface PvPPlayerFilterUiModels

data class HeaderPvP(
  val title: String,
) : PvPPlayerFilterUiModels

data class PvPPlayerFilterMonthModel(
  val monthName: String,
  val isChecked: Boolean,
  val isEven: Int,
) : PvPPlayerFilterUiModels
