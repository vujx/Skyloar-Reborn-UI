package com.example

import android.content.Context
import androidx.core.content.ContextCompat
import com.readystatesoftware.chuck.internal.ui.MainActivity

class Dictionary(private val ctxApp: Context) {

  fun getStringRes(id: Int) = ctxApp.getString(id)
  fun getStringArray(id: Int): Array<String> = ctxApp.resources.getStringArray(id)

  fun getColorRes(id: Int) = ContextCompat.getColor(ctxApp, id)
  fun getImage(activity: MainActivity, id: Int) = ContextCompat.getDrawable(activity, id)
}
