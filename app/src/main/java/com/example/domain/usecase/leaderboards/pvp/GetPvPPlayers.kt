package com.example.domain.usecase.leaderboards.pvp

import com.example.App
import com.example.R
import com.example.data.mapper.PvPPlayer1v1Mapper
import com.example.data.model.leaderboards.PlayerPvPEntityItem
import com.example.domain.model.PvPPlayer1v1
import com.example.domain.repository.leaderboard.pvp.PvPRepository
import com.example.domain.usecase.BaseUseCase
import com.example.util.HandleCallbackError
import java.lang.Exception

class GetPvPPlayers(
    private val pvpRepo: PvPRepository
) : BaseUseCase<List<Any>, List<PvPPlayer1v1>?> {

    private val pvpMapper = PvPPlayer1v1Mapper()
    override suspend fun execute(
        params: List<Any>,
        callback: BaseUseCase.Callback<List<PvPPlayer1v1>?>
    ) {
        try {
            val response = pvpRepo.getPvPPlayers(
                params[0] as String,
                params[1] as Int,
                params[2] as Int,
                params[3] as Int
            )

            when (response.code()) {
                200 -> {
                    response.body()?.let { result ->
                        callback.onSuccess(result.map {
                            pvpMapper.mapFromEntity(it)
                        })
                    } ?: callback.onError(App.getStringResource(R.string.unexpected_error))
                }
                400 -> callback.onError(App.getStringResource(R.string.pvp_players_not_found))
                401 -> callback.onSuccess(null)
                else -> callback.onError(App.getStringResource(R.string.unexpected_error))
            }
        } catch (e: Exception) {
            HandleCallbackError<List<PvPPlayer1v1>?>().handleOnErrorCallback(e, callback)
        }
    }
}