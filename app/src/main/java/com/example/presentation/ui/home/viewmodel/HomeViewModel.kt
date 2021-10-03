package com.example.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.home.GetHomeIntroText
import com.example.util.Result
import com.example.util.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(
  private val homeIntroText: GetHomeIntroText
): ViewModel() {

  val getIntroText: SingleLiveEvent<String> = SingleLiveEvent()

  init {
    getIntroText()
  }

  private fun getIntroText() {
    viewModelScope.launch {
      when(val result = homeIntroText()) {
        is Result.Success -> getIntroText.postValue(result.data)
        is Result.Error -> getIntroText.postValue(null)
      }
    }
  }
}