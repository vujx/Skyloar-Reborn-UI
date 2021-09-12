package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.example.R
import com.example.data.usecase.leaderboards.PvPUseCase
import com.example.domain.model.PvPPlayer
import com.example.domain.usecase.BaseUseCase
import com.example.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PvPPlayerViewModel(private val useCasePvp: PvPUseCase) :
    ViewModel(),
    BaseUseCase.Callback<List<PvPPlayer>?> {

    private val _pvpPlayer = MutableLiveData<Resource<List<PvPPlayer>?>>()
    val pvpPlayer: LiveData<Resource<List<PvPPlayer>?>> = _pvpPlayer

    val numOfSearchResult = MutableLiveData<Int>()
    val pageResult = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { ctx, _ ->
        onError(App.getStringResource(R.string.unexpected_error))
        ctx.cancel()
    }

    fun getPvPPlayers(
        type: String,
        month: Int,
        page: Int,
        number: Int
    ) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _pvpPlayer.postValue(Resource.Loading())
            useCasePvp.getPvPPlayers.execute(listOf(type, month, page, number), this@PvPPlayerViewModel)
        }

        getNumOfPvPSearchResult(type, month, page)
    }

    private fun getNumOfPvPSearchResult(
        type: String,
        month: Int,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        val countSearch =
            useCasePvp.getNumOfPvPSearchResult.execute(type, month)
        val numOfPage = (countSearch.toDouble() / 20)
        if (numOfPage % 1 == 0.0)
            pageResult.postValue("$page / ${((countSearch / 20))}")
        else
            pageResult.postValue("$page / ${((countSearch / 20) + 1)}")
        numOfSearchResult.postValue(countSearch)
    }

    override fun onSuccess(result: List<PvPPlayer>?) {
        result?.let {
            if (it.isEmpty()) _pvpPlayer.postValue(Resource.Empty())
            else _pvpPlayer.postValue(Resource.Success(it))
        } ?: _pvpPlayer.postValue(Resource.Success(null))
    }

    override fun onError(errorMessage: String) {
        _pvpPlayer.postValue(Resource.Failure(errorMessage))
    }
}
