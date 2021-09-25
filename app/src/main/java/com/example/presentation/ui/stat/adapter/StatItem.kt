package com.example.presentation.ui.stat.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.Dictionary
import com.example.R
import com.example.databinding.ItemStatInfoBinding
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
    statCount: Long?,
    statPos: Int,
  ) {
    binding.tvStatTitle.text = dictionary.getStringArray(R.array.statTitle)[statPos].toString()
    binding.tvStatValue.text = statCount?.toString() ?: dictionary.getStringRes(R.string.not_found_stat_value)
  }
}
