package com.example.util

import android.content.Context
import android.view.Gravity
import android.widget.Toast

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
