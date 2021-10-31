package com.example.presentation.ui.dialogs.searchPvEPlayers

class MapPvEPlayerData {

  fun map(
    map: MutableMap<Int, String>,
    month: MutableMap<Int, String>,
    type: Int?,
  ): MutableList<PvEPlayerFilterUiModels> {
    val content = mutableListOf<PvEPlayerFilterUiModels>()
    type?.let {
      when(type) {
        2 -> {
          content.add(PvEPlayerHeader("Player"))
          content.add(PvEPlayerType("1", false, 0))
          content.add(PvEPlayerType("2", false, 1))
        }
        4 -> {
          content.add(PvEPlayerHeader("Player"))
          content.add(PvEPlayerType("1", false, 0))
          content.add(PvEPlayerType("2", false, 1))
          content.add(PvEPlayerType("3", false, 2))
          content.add(PvEPlayerType("4", false, 3))
        }
        else -> {}
      }
    }

    var position = 0
    if(month.isNotEmpty()) {
      content.add(PvEPlayerHeader("Month"))
      month.forEach { value ->
        content.add(PvEPlayerMonth(value.value, false, position))
        position++
      }
      position = 0
    }

    if(map.isNotEmpty()) {
      content.add(PvEPlayerHeader("Maps"))
      map.forEach { value ->
        content.add(PvEPlayerMap(value.value, false, position))
        position++
      }
    }

    content.add(PvEPlayerButton)

    return content
  }

  fun update(
    filterList: List<PvEPlayerFilterUiModels>,
    item: PvEPlayerFilterUiModels,
  ): List<PvEPlayerFilterUiModels> {
    return filterList.map { filterModel ->
      when(filterModel) {
        is PvEPlayerMonth -> {
          when(item) {
            is PvEPlayerMonth -> {
              if(item == filterModel) PvEPlayerMonth(item.monthName, true, item.isEven)
              else PvEPlayerMonth(filterModel.monthName, false, filterModel.isEven)
            }
            else -> filterModel
          }
        }
        is PvEPlayerMap -> {
          when(item) {
            is PvEPlayerMap -> {
              if(item == filterModel) PvEPlayerMap(item.mapName, true, item.isEven)
              else PvEPlayerMap(filterModel.mapName, false, filterModel.isEven)
            }
            else -> filterModel
          }
        }
        is PvEPlayerType -> {
          when(item) {
            is PvEPlayerType -> {
              if(item == filterModel) PvEPlayerType(item.type, true, item.isEven)
              else PvEPlayerType(filterModel.type, false, filterModel.isEven)
            }
            else -> filterModel
          }
        }
        else -> filterModel
      }
    }
  }
}