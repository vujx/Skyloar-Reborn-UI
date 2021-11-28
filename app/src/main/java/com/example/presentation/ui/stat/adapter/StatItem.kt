package com.example.presentation.ui.stat.adapter

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.Dictionary
import com.example.R
import com.example.data.model.stat.StatEntity
import com.example.databinding.ItemStatInfoBinding
import com.example.util.numberWithCommas
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StatItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr),
  KoinComponent {

  private val dictionary: Dictionary by inject()

  private val binding: ItemStatInfoBinding = ItemStatInfoBinding.inflate(
    LayoutInflater.from(context),
    this,
  )

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  fun bind(
    statValues: MutableMap<String, StatEntity?>,
    statPos: Int,
  ) {
    if (statPos % 2 == 0)
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey_lighter))
    else
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey))
    val keyMap = dictionary.getStringArray(R.array.statPaths)[statPos]
    binding.tvStatTitle.text = dictionary.getStringArray(R.array.statTitle)[statPos]
    binding.tvStatValue.text = statValues[keyMap]?.let {
      val value = numberWithCommas((it.count as Double).toLong())
      value
    } ?: dictionary.getStringRes(R.string.not_found_stat_value)
  }
}
