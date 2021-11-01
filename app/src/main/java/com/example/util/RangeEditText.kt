package com.example.util

import android.text.InputFilter
import android.text.Spanned

class RangeEditText : InputFilter {

  private var min: Long = 0
  private var max: Long = 0

  constructor(min: Long, max: Long) {
    this.min = min
    this.max = max
  }

  constructor(min: String, max: String) {
    this.min = min.toLong()
    this.max = max.toLong()
  }

  override fun filter(
    source: CharSequence,
    start: Int,
    end: Int,
    dest: Spanned,
    dstart: Int,
    dend: Int
  ): CharSequence? {
    try {
      val input = Integer.parseInt(dest.toString() + source.toString())
      if (isInRange(min, max, input))
        return null
    } catch (nfe: NumberFormatException) {
    }
    return ""
  }

  private fun isInRange(a: Long, b: Long, c: Int): Boolean {
    return if (b > a) c in a..b else c in b..a
  }
}
