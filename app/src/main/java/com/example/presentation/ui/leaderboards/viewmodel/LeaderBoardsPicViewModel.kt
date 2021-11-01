package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.ViewModel
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.Content
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.Loading
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.NavigateToPvePlayers
import com.example.util.SingleLiveEvent

class LeaderBoardsPicViewModel(
  private val mapper: LeaderBoardsMapper
) : ViewModel() {

  val viewState = SingleLiveEvent<LeaderBoardsViewState>()

  fun requestData() {
    viewState.postValue(Loading)
    viewState.postValue(Content(mapper.map()))
  }

  fun onLeaderBoardsClick(type: Int) {
    viewState.postValue(NavigateToPvePlayers(type))
  }
}