package com.example

import android.content.Context

class Dictionary(private val ctxApp: Context) {

  fun getStringRes(id: Int) = ctxApp.getString(id)
}