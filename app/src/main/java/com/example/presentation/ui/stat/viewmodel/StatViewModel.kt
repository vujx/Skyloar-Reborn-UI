package com.example.presentation.ui.stat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.error.ErrorEntity
import com.example.domain.usecase.stat.GetStatValues
import com.example.util.Resource
import kotlinx.coroutines.launch
import com.example.util.Result

class StatViewModel(private val getStatValues: GetStatValues) :
  ViewModel() {

  private val _statValues = MutableLiveData<Resource<List<Long?>>>()
  val statValues: LiveData<Resource<List<Long?>>> = _statValues

  init {
    getListOfStatValues()
  }

  fun getListOfStatValues() = viewModelScope.launch {
    _statValues.postValue(Resource.Loading())
    val result = getStatValues()
    if (result.isEmpty()) _statValues.postValue(Resource.Empty())
    else {
      val resultStat = result.map {
        when (it) {
          is Result.Success -> (it.data.count as Double).toLong()
          is Result.Error -> null
        }
      }
      if (resultStat.all { it == null }) _statValues.postValue(Resource.Failure(ErrorEntity.Network)) else _statValues.postValue(Resource.Success(resultStat))
    }
  }
}
