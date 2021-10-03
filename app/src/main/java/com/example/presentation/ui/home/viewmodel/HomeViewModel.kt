package com.example.presentation.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.home.GetHomeIntroText
import com.example.presentation.MainActivity
import com.example.util.Resource
import com.example.util.Result
import com.example.util.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(
  private val homeIntroText: GetHomeIntroText
): ViewModel() {

  val getIntroText: SingleLiveEvent<Resource<String>> = SingleLiveEvent()

  init {
    getIntroText()
  }

  fun getIntroText() {
    viewModelScope.launch {
      getIntroText.postValue(Resource.Loading())
      if(MainActivity.homeIntroText.isEmpty()) {
        when(val result = homeIntroText()) {
          is Result.Success -> {
            getIntroText.postValue(Resource.Success(result.data))
            MainActivity.homeIntroText = result.data
          }
          is Result.Error -> getIntroText.postValue(Resource.Failure(result.error))
        }
      } else getIntroText.postValue(Resource.Success(MainActivity.homeIntroText))
    }
  }
}