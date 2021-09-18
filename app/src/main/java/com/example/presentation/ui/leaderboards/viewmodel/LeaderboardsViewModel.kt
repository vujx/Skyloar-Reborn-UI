package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.usecase.leaderboards.LeaderBoardsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LeaderboardsViewModel(private val useCaseLeaderboards: LeaderBoardsUseCase) : ViewModel() {

  val listOfRanges = MutableLiveData<Map<Int, String>>()

  private val exceptionHandler = CoroutineExceptionHandler { ctx, _ ->
    ctx.cancel()
  }

  fun getRange(path: String) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
    listOfRanges.postValue(useCaseLeaderboards.getRanges.execute(path))
  }

  fun getMaps(type: Int) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
    useCaseLeaderboards.getMaps.execute(type)
  }
}
