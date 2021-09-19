package com.example.util

import com.example.presentation.MainActivity.Companion.listOfMonth

fun getMonthValueByName(month: String): Int {
  var monthRange = 0
  listOfMonth?.let { result ->
    result.forEach {
      if (it.value == month)
        monthRange = it.key
    }
  }
  return monthRange
}

fun getTypePvP(type: String): String =
  if (type == "1vs1")
    "1v1"
  else
    "2v2"
