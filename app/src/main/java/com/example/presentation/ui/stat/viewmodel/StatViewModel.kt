package com.example.presentation.ui.stat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.example.R
import com.example.data.usecase.StatUseCase
import com.example.domain.usecase.BaseUseCase
import com.example.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatViewModel(private val statUseCase: StatUseCase) :
    ViewModel(),
    BaseUseCase.Callback<List<Int>> {

    private val _statValues = MutableLiveData<Resource<List<Int>>>()
    val statValues: LiveData<Resource<List<Int>>> = _statValues

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        onError(App.getStringResource(R.string.unexpected_error))
    }

    fun getListOfStatValues() = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        _statValues.postValue(Resource.Loading())
        statUseCase.getListOfStatValues.execute(null, this@StatViewModel)
    }

    override fun onSuccess(result: List<Int>) {
        if (result.isEmpty()) _statValues.postValue(Resource.Empty())
        else _statValues.postValue(Resource.Success(result))
    }

    override fun onError(errorMessage: String) {
        _statValues.postValue(Resource.Failure(errorMessage))
    }
}