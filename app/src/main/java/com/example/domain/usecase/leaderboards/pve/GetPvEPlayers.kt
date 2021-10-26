package com.example.domain.usecase.leaderboards.pve

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

  suspend operator fun invoke(
    type: Int,
    players: Int,
    map: Int,
    month: Int,
    page: Int,
    number: Int
  ): Result<PvEData> =
    withContext(Dispatchers.IO) {
      withTimeoutOrNull(5000) {
        var currentMap = map
        if(map == -1) {
          when(val result = leaderboardsRepo.getMaps(type)) {
            is Success -> currentMap = result.data.keys.elementAt(0)
            is Error -> Error(result.error)
          }
        }
        listOf(async {
          listOfPvePlayer = pveRepo.getPvEPlayers(
            type,
            players,
            currentMap,
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
          async {
            maps = leaderboardsRepo.getMaps(type)
          },
          async {
            difficulties = leaderboardsRepo.getRange("difficulties")
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
