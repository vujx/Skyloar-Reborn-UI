package com.example.domain.usecase.leaderboards.pve

import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.domain.error.ErrorEntity
import com.example.domain.model.PvEPlayer
import com.example.domain.repository.leaderboard.pve.PvERepository
import com.example.util.Result
import com.example.util.Result.Error
import com.example.util.Result.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull

class GetPvEPlayers(
  private val pveRepo: PvERepository,
) {

  private var listOfPvePlayer: Result<List<PvEPlayer>?>? = null
  private var numberOfResults: Result<NumberOfSearchResultsEntity>? = null

  suspend operator fun invoke(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: Int,
    number: Int,
  ): Result<PvEData> =
    withContext(Dispatchers.IO) {
      withTimeoutOrNull(5000) {
        listOf(
          async {
            listOfPvePlayer = pveRepo.getPvEPlayers(
              type,
              players,
              map,
              month,
              page,
              number,
            )
          },
          async {
            numberOfResults = pveRepo.getNumOfPvESearchResult(
              type,
              players,
              map,
              month,
            )
          },
        ).awaitAll()
      } ?: return@withContext Error(ErrorEntity.ServiceUnavailable)

      return@withContext if (listOfPvePlayer is Success && numberOfResults is Success) {
        val data = PvEData(
          (listOfPvePlayer as Success<List<PvEPlayer>?>).data,
          (numberOfResults as Success<NumberOfSearchResultsEntity>).data,
        )
        Success(data)
      } else if (listOfPvePlayer is Error) Error((listOfPvePlayer as Error).error)
      else Error((numberOfResults as Error).error)
    }

  data class PvEData(
    val pvePlayers: List<PvEPlayer>?,
    val numberOfSearchResults: NumberOfSearchResultsEntity,
  )
}
