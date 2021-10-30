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
          content.add(PvEPlayerType("1", false))
          content.add(PvEPlayerType("2", false))
        }
        4 -> {
          content.add(PvEPlayerHeader("Player"))
          content.add(PvEPlayerType("1", false))
          content.add(PvEPlayerType("2", false))
          content.add(PvEPlayerType("3", false))
          content.add(PvEPlayerType("4", false))
        }
        else -> {}
      }
    }

    if(month.isNotEmpty()) {
      content.add(PvEPlayerHeader("Month"))
      month.forEach { value ->
        content.add(PvEPlayerMonth(value.value, false))
      }
    }

    if(map.isNotEmpty()) {
      content.add(PvEPlayerHeader("Maps"))
      map.forEach { value ->
        content.add(PvEPlayerMap(value.value, false))
      }
    }

    content.add(PvEPlayerButton)

    return content
  }
}