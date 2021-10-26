package com.example.domain.usecase.leaderboards.pve

import android.util.Log
import com.example.data.model.auction.NumberOfSearchResultsEntity
import com.example.domain.error.ErrorEntity
import com.example.domain.model.PvEPlayer
import com.example.domain.repository.leaderboard.LeaderboardRepository
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
  private val leaderboardsRepo: LeaderboardRepository,
) {

  private var listOfPvePlayer: Result<List<PvEPlayer>?>? = null
  private var numberOfResults: Result<NumberOfSearchResultsEntity>? = null
  private var maps: Result<MutableMap<Int, String>>? = null
  private var difficulties: Result<MutableMap<Int, String>>? = null

  suspend operator fun invoke(params: List<Int>): Result<PvEData> =
    withContext(Dispatchers.IO) {
      withTimeoutOrNull(5000) {
        listOf(async {
          listOfPvePlayer = pveRepo.getPvEPlayers(
            params[0],
            params[1],
            params[2],
            params[3],
            params[4],
            params[5]
          )
          Log.d("ispis", "1: ${listOfPvePlayer}")
        },
          async {
            numberOfResults = pveRepo.getNumOfPvESearchResult(
              params[0],
              params[1],
              params[2],
              params[3],
            )
            Log.d("ispis", "2: ${numberOfResults}")
          },
          async {
            maps = leaderboardsRepo.getMaps(params[0])
            Log.d("ispis", "3: ${maps}")
          },
          async {
            difficulties = leaderboardsRepo.getRange("difficulties")
            Log.d("ispis", "$4: {difficulties}")
          }).awaitAll()
      } ?: return@withContext Error(ErrorEntity.ServiceUnavailable)

      return@withContext if (
        listOfPvePlayer is Success && numberOfResults is Success
        && maps is Success && difficulties is Success
      ) {
        val data = PvEData(
          (listOfPvePlayer as Success<List<PvEPlayer>?>).data,
          (numberOfResults as Success<NumberOfSearchResultsEntity>).data,
          (maps as Success<MutableMap<Int, String>>).data,
          (difficulties as Success<MutableMap<Int, String>>).data)
        Success(data)
      }
      else if (listOfPvePlayer is Error) Error((listOfPvePlayer as Error).error)
      else if (numberOfResults is Error) Error((numberOfResults as Error).error)
      else if (maps is Error) Error((maps as Error).error)
      else Error((difficulties as Error).error)
    }

  data class PvEData(
    val pvePlayers: List<PvEPlayer>?,
    val numberOfSearchResults: NumberOfSearchResultsEntity,
    val maps: MutableMap<Int, String>,
    val difficulties: MutableMap<Int, String>,
  )
}
