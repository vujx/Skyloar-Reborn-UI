package com.example.presentation.ui.dialogs.searchPvEPlayers

interface PvEPlayerFilterUiModels

data class PvEPlayerHeader(
  val title: String,
) : PvEPlayerFilterUiModels

data class PvEPlayerMonth(
  val monthName: String,
  val isChecked: Boolean,
  val isEven: Int,
) : PvEPlayerFilterUiModels

data class PvEPlayerMap(
  val mapName: String,
  val isChecked: Boolean,
  val isEven: Int,
) : PvEPlayerFilterUiModels

data class PvEPlayerType(
  val type: String,
  val isChecked: Boolean,
  val isEven: Int,
) : PvEPlayerFilterUiModels

object PvEPlayerButton : PvEPlayerFilterUiModels
