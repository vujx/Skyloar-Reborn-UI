package com.example.domain.usecase.leaderboards.pvp

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.domain.error.ErrorEntity
import com.example.domain.model.PvPPlayer
import com.example.domain.repository.leaderboard.pvp.PvPRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.util.Result
import com.example.util.Result.Error
import com.example.util.Result.Success
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withTimeoutOrNull

class GetPvPPlayers(
  private val pvpRepo: PvPRepository
) {

  private var listOfPvPPlayer: Result<List<PvPPlayer>?>? = null
  private var numberOfResults: Result<NumberOfSearchResultsEntity>? = null

  suspend operator fun invoke(params: List<Any>): Result<PvPData> = withContext(Dispatchers.IO) {
    withTimeoutOrNull(4000) {
      listOf(
        async {
          listOfPvPPlayer = pvpRepo.getPvPPlayers(
            params[0] as String,
            params[1] as Int,
            params[2] as Int,
            params[3] as Int
          )
        },
        async {
          numberOfResults = pvpRepo.getNumOfPvPSearchResult(
            params[0] as String,
            params[1] as Int,
          )
        }
      ).awaitAll()
    } ?: return@withContext Error(ErrorEntity.ServiceUnavailable)

    return@withContext if(listOfPvPPlayer is Success && numberOfResults is Success) {
      val data = PvPData(
        (listOfPvPPlayer as Success<List<PvPPlayer>?>).data,
        (numberOfResults as Success<NumberOfSearchResultsEntity>).data,
      )
      Success(data)
    } else if (listOfPvPPlayer is Error) Error((listOfPvPPlayer as Error).error)
    else {
      Error((numberOfResults as Error).error)
    }
  }

  data class PvPData(
    val pvpPlayers: List<PvPPlayer>?,
    val numberOfSearchResultsEntity: NumberOfSearchResultsEntity,
  )
}
