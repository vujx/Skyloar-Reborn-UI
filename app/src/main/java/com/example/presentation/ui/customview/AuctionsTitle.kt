package com.example.presentation.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.Dictionary
import com.example.R
import com.example.databinding.ItemAuctionInfoBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuctionsTitle @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr),
  KoinComponent {

  private val dictionary: Dictionary by inject()

  private val binding = ItemAuctionInfoBinding.inflate(
    LayoutInflater.from(context),
    this,
  )

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    binding.root.setBackgroundColor(dictionary.getColorRes(R.color.darkred))
    bindTitle()
  }

  fun bindTitle() {
    binding.tvPlayer.text = dictionary.getStringRes(R.string.player)
    binding.tvRating.text = dictionary.getStringRes(R.string.rating)
    binding.tvELO.text = dictionary.getStringRes(R.string.elo)
    binding.tvActivity.text = dictionary.getStringRes(R.string.buyout)
    binding.tvMatches.text = dictionary.getStringRes(R.string.end)
  }
}