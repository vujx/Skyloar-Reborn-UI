package com.example.presentation.ui.helper.leaderboards

import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.util.getMonthValueByName
import com.example.util.getTypePvP

class PvPOnClickHelper {

  fun onNextBtnPress(viewModel: PvPPlayerViewModel, type: String, page: String, monthValue: String) {
    val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
    val lastPage = page.substring(numOfPage.toString().length + 3).toInt()
    val monthRange = getMonthValueByName(monthValue)
    if (numOfPage == lastPage)
      viewModel.getPvPPlayers(getTypePvP(type), monthRange, 1, 20)
    else
      viewModel.getPvPPlayers(getTypePvP(type), monthRange, numOfPage + 1, 20)
  }

  fun onBackBtnPress(viewModel: PvPPlayerViewModel, type: String, page: String, monthValue: String) {
    val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
    val lastPage = page.substring(numOfPage.toString().length + 3).toInt()
    val monthRange = getMonthValueByName(monthValue)
    if (numOfPage == 1)
      viewModel.getPvPPlayers(getTypePvP(type), monthRange, lastPage, 20)
    else
      viewModel.getPvPPlayers(getTypePvP(type), monthRange, numOfPage - 1, 20)
  }

  fun onSearchBtnPress(viewModel: PvPPlayerViewModel, type: String, page: String, monthValue: String) {
    val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
    val monthRange = getMonthValueByName(monthValue)
    viewModel.getPvPPlayers(getTypePvP(type), monthRange, numOfPage, 20)
  }
}
