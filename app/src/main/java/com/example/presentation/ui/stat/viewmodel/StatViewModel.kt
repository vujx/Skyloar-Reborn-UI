package com.example.presentation.ui.stat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.stat.StatEntity
import com.example.domain.usecase.stat.GetStatValues
import com.example.util.Resource
import com.example.util.Result.Error
import com.example.util.Result.Success
import kotlinx.coroutines.launch

class StatViewModel(private val getStatValues: GetStatValues) :
  ViewModel() {

  private val _statValues = MutableLiveData<Resource<List<StatEntity?>>>()
  val statValues: LiveData<Resource<List<StatEntity?>>> = _statValues

  init {
    getListOfStatValues()
  }

  fun getListOfStatValues() = viewModelScope.launch {
    _statValues.postValue(Resource.Loading())
    when (val result = getStatValues()) {
      is Success -> _statValues.postValue(Resource.Success(result.data))
      is Error -> _statValues.postValue(Resource.Failure(result.error))
    }
  }
}
