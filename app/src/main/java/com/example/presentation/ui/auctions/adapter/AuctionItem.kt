package com.example.presentation.ui.auctions.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.Dictionary
import com.example.R
import com.example.data.model.auction.AuctionEntityItem
import com.example.databinding.ItemAuctionInfoBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuctionItem @JvmOverloads constructor(
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
  }

  fun bind(auction: AuctionEntityItem, position: Int) {
    if (position % 2 == 0)
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey_lighter))
    else
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey))
    binding.tvPlayer.text = auction.cardName
    binding.tvRating.text = auction.startingPrice.toString()
    binding.tvELO.text = auction.currentPrice.toString()
    binding.tvActivity.text = auction.buyoutPrice.toString()
    binding.tvMatches.text = auction.endingOn
  }
}
