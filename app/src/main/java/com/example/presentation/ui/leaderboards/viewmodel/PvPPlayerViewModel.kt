package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.example.R
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.domain.usecase.BaseUseCase
import com.example.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler

class PvPPlayerViewModel():
    ViewModel(),
    BaseUseCase.Callback<List<PlayerPvPEntityItem>> {

    private val _pvpPlayer = MutableLiveData<Resource<List<PlayerPvPEntityItem>>>()
    val pvpPlayer: LiveData<Resource<List<PlayerPvPEntityItem>>> = _pvpPlayer

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        onError(App.getStringResource(R.string.unexpected_error))
    }

    override fun onSuccess(result: List<PlayerPvPEntityItem>) {
        _pvpPlayer.postValue(Resource.Loading())
    }

    override fun onError(errorMessage: String) {
        _pvpPlayer.postValue(Resource.Failure(errorMessage))
    }
}