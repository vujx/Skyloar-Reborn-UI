package com.example

import android.content.Context
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class Dictionary(private val ctxApp: Context) {

  fun getStringRes(id: Int) = ctxApp.getString(id)
  fun getStringArray(id: Int): Array<String> = ctxApp.resources.getStringArray(id)

  fun getColorRes(id: Int) = ContextCompat.getColor(ctxApp, id)
}
