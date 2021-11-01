package com.example.presentation.ui.dialogs.searchPvEPlayers

import android.util.Log

class MapPvEPlayerData {

  fun map(
    map: MutableMap<Int, String>,
    month: MutableMap<Int, String>,
    type: Int,
    realType: Int,
    selectMap: Int,
    selectMonth: Int,
  ): MutableList<PvEPlayerFilterUiModels> {
    val content = mutableListOf<PvEPlayerFilterUiModels>()
      when(type) {
        2 -> {
          content.add(PvEPlayerHeader("Player"))
          (1..2).forEach {
            if(realType == it) {
              content.add(PvEPlayerType(it.toString(), true, it))
            } else content.add(PvEPlayerType(it.toString(), false, it))
          }
        }
        4 -> {
          content.add(PvEPlayerHeader("Player"))
          (1..4).forEach {
            if(realType == it) {
              content.add(PvEPlayerType(it.toString(), true, it))
            } else content.add(PvEPlayerType(it.toString(), false, it))
          }
        }
        else -> {}
    }

    var position = 0
    if(month.isNotEmpty()) {
      content.add(PvEPlayerHeader("Month"))
      month.forEach { value ->
        if(value.key == selectMonth)
          content.add(PvEPlayerMonth(value.value, true, position))
        else
          content.add(PvEPlayerMonth(value.value, false, position))
        position++
      }
      position = 0
    }

    if(map.isNotEmpty()) {
      content.add(PvEPlayerHeader("Maps"))
      map.forEach { value ->
        if(value.key == selectMap)
          content.add(PvEPlayerMap(value.value, true, position))
        else content.add(PvEPlayerMap(value.value, false, position))
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

  fun getSearchResult(
    filterList: List<PvEPlayerFilterUiModels>,
    month: MutableMap<Int, String>,
    map: MutableMap<Int, String>,
    page: Int,
  ): PvESearchResult {
    var selectedType = 0
    var selectedMonth = 0
    var selectedMap = 0
    filterList.forEach { filterModel ->
      when(filterModel) {
        is PvEPlayerMonth -> {
          if(filterModel.isChecked) {
            month.forEach { value ->
              Log.d("ispis", "${value.value} I ${filterModel.monthName}")
              if(filterModel.monthName == value.value){
                selectedMonth = value.key
              }

            }
          }
        }
        is PvEPlayerType -> {
          if(filterModel.isChecked) selectedType = filterModel.type.toInt()
        }
        is PvEPlayerMap -> {
          if(filterModel.isChecked) {
            map.forEach { value ->
              if(filterModel.mapName == value.value)
                selectedMap = value.key
            }
          }
        }
      }
    }
    return PvESearchResult(selectedType, selectedMonth, selectedMap, page)
  }
}