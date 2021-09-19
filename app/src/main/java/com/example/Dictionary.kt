package com.example

import android.content.Context

class Dictionary(private val ctxApp: Context) {

  fun getStringRes(id: Int) = ctxApp.getString(id)
  fun getStringArray(id: Int) = ctxApp.resources.getStringArray(id)
}
