package com.example.presentation.ui.dialogs.searchPvEPlayers

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class PvEFilterViewState {

  object Loading : PvEFilterViewState()
  data class Content(val filterList: List<PvEPlayerFilterUiModels>) : PvEFilterViewState()
  data class NavigateToPvEFragment(
    val searchValue: PvESearchResult
  ) : PvEFilterViewState()
}

@Parcelize
data class PvESearch(
  val maps: MutableMap<Int, String>,
  val months: MutableMap<Int, String>,
  val realType: Int,
  val type: Int,
  val month: Int,
  val map: Int
) : Parcelable

@Parcelize
data class PvESearchResult(
  val type: Int,
  val month: Int,
  val map: Int
) : Parcelable