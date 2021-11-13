package com.example.presentation.ui.dialogs.searchPvPPlayers

class MapPvPPlayerData {

  fun map(
    month: MutableMap<Int, String>,
    selectMonth: Int,
  ): MutableList<PvPPlayerFilterUiModels> {
    val content = mutableListOf<PvPPlayerFilterUiModels>()
    var position = 0
    content.add(HeaderPvP("Month"))
    month.forEach { value ->
      if (value.key == selectMonth)
        content.add(PvPPlayerFilterMonthModel(
          value.value, true, position
        ))
      else
        content.add(PvPPlayerFilterMonthModel(
          value.value, false, position
        ))
      position++
    }
    return content
  }

  fun update(
    filterListView: List<PvPPlayerFilterUiModels>,
    item: PvPPlayerFilterUiModels,
  ): List<PvPPlayerFilterUiModels> {
    return filterListView.map { filterModel ->
      when (filterModel) {
        is PvPPlayerFilterMonthModel ->
          when (item) {
            is PvPPlayerFilterMonthModel -> {
              if (item == filterModel) PvPPlayerFilterMonthModel(item.monthName, true, item.isEven)
              else PvPPlayerFilterMonthModel(filterModel.monthName, false, filterModel.isEven)
            }
            else -> filterModel
          }
        else -> filterModel
      }
    }
  }

  fun getSearchResult(
    filterList: List<PvPPlayerFilterUiModels>,
    month: MutableMap<Int, String>,
  ): Int {
    filterList.forEach { filterModel ->
      when(filterModel) {
        is PvPPlayerFilterMonthModel -> {
          if (filterModel.isChecked) {
            month.forEach { value ->
              if(filterModel.monthName == value.value) {
                return value.key
              }
            }
          }
        }
      }
    }
    return 0
  }
}