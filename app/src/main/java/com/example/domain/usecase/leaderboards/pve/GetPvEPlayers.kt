package com.example.domain.usecase.leaderboards.pve

import com.example.App
import com.example.R
import com.example.data.mapper.PvEPlayerMapper
import com.example.domain.model.PvEPlayer
import com.example.domain.repository.leaderboard.pve.PvERepository
import com.example.domain.usecase.BaseUseCase
import com.example.util.HandleCallbackError

class GetPvEPlayers(private val pveRepo: PvERepository) :
    BaseUseCase<List<Int>, List<PvEPlayer>?> {

    private val mapper = PvEPlayerMapper()

    override suspend fun execute(
        params: List<Int>,
        callback: BaseUseCase.Callback<List<PvEPlayer>?>
    ) {
        try {
            val response = pveRepo.getPvEPlayers(
                params[0],
                params[1],
                params[2],
                params[3],
                params[4],
                params[5]
            )

            when (response.code()) {
                200 -> response.body()?.let { result ->
                    callback.onSuccess(
                        result.map {
                            mapper.mapFromEntity(it)
                        }
                    )
                } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
                400 -> callback.onError(App.getStringResource(R.string.pvp_players_not_found))
                401 -> callback.onSuccess(null)
                else -> callback.onError(App.getStringResource(R.string.unexpected_error))
            }
        } catch (e: Exception) {
            HandleCallbackError<List<PvEPlayer>?>().handleOnErrorCallback(e, callback)
        }
    }
}
