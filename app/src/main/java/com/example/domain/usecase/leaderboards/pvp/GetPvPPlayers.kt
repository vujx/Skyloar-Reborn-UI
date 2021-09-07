package com.example.domain.usecase.leaderboards.pvp

import com.example.App
import com.example.R
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.domain.repository.leaderboard.pvp.PvPRepository
import com.example.domain.usecase.BaseUseCase
import com.example.util.HandleCallbackError
import java.lang.Exception

class GetPvPPlayers(
    private val pvpRepo: PvPRepository
) : BaseUseCase<List<Int>, List<PlayerPvPEntityItem>?> {
    override suspend fun execute(
        params: List<Int>,
        callback: BaseUseCase.Callback<List<PlayerPvPEntityItem>?>
    ) {
        try {
            val response = pvpRepo.getPvPPlayers(
                params[0], params[1], params[2], params[3]
            )

            when(response.code()) {
                200 -> {
                    response.body()?.let { result ->
                        callback.onSuccess(result)
                    } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
                }
                400 -> callback.onError(App.getStringResource(R.string.pvp_players_not_found))
                401 -> callback.onSuccess(null)
                else -> callback.onError(App.getStringResource(R.string.unexpected_error))
            }
        } catch (e: Exception) {
            HandleCallbackError<List<PlayerPvPEntityItem>?>().handleOnErrorCallback(e, callback)
        }
    }
}