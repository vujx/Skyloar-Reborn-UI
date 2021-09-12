package com.example.presentation.ui.leaderboards.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.App
import com.example.R
import com.example.data.usecase.leaderboards.PvEUseCase
import com.example.domain.model.PvEPlayer
import com.example.domain.usecase.BaseUseCase
import com.example.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class PvEPlayerViewModel(private val useCasePvE: PvEUseCase) :
    ViewModel(),
    BaseUseCase.Callback<List<PvEPlayer>?> {

    private val _pvePlayer = MutableLiveData<Resource<List<PvEPlayer>?>>()
    val pvePlayer: LiveData<Resource<List<PvEPlayer>?>> = _pvePlayer

    val numOfSearchResult = MutableLiveData<Int>()
    val pageResult = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { ctx, _ ->
        onError(App.getStringResource(R.string.unexpected_error))
        ctx.cancel()
    }

    fun getPvEPlayers(
        type: Int,
        players: Int,
        map: Int,
        month: Int,
        page: Int,
        number: Int
    ) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _pvePlayer.postValue(Resource.Loading())
            useCasePvE.getPvEPlayers.execute(
                listOf(type, players, map, month, page, number),
                this@PvEPlayerViewModel
            )
        }
        getNumOfPvPSearchResult(type, players, map, month, page)
    }

    private fun getNumOfPvPSearchResult(
        type: Int,
        players: Int,
        map: Int,
        month: Int,
        page: Int
    ) = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        val countSearch =
            useCasePvE.getNumOfPvESearchResult.execute(type, players, map, month)
        val numOfPage = (countSearch.toDouble() / 20)
        if (numOfPage % 1 == 0.0)
            pageResult.postValue("$page / ${((countSearch / 20))}")
        else
            pageResult.postValue("$page / ${((countSearch / 20) + 1)}")
        numOfSearchResult.postValue(countSearch)
    }

    override fun onSuccess(result: List<PvEPlayer>?) {
        result?.let {
            if (it.isEmpty()) _pvePlayer.postValue(Resource.Empty())
            else _pvePlayer.postValue(Resource.Success(it))
        } ?: _pvePlayer.postValue(Resource.Success(null))
    }

    override fun onError(errorMessage: String) {
        _pvePlayer.postValue(Resource.Failure(errorMessage))
    }
}
