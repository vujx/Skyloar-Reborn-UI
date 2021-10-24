package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.Content
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.Loading

class LeaderBoardsPicViewModel(
  private val mapper: LeaderBoardsMapper
) : ViewModel() {

  val viewState = MutableLiveData<LeaderBoardsViewState>()

  fun requestData() {
    viewState.postValue(Loading)
    viewState.postValue(Content(mapper.map()))
  }
}