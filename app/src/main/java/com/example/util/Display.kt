package com.example.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast
import com.example.presentation.MainActivity.Companion.listOfMonth

fun displayMessage(message: String, context: Context) {
  val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
  toast.setGravity(Gravity.CENTER, 0, 0)
  toast.show()
}

fun checkIfInputIsEmpty(input: String): Int? =
  if (input.isBlank())
    null
  else
    Integer.parseInt(input)

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
