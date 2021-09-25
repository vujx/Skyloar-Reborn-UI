package com.example.presentation.ui.auctions.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.data.model.auction.AuctionEntityItem
import com.example.databinding.ItemAuctionInfoBinding
import org.koin.core.component.KoinComponent

class AuctionItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr),
  KoinComponent {

  private val binding = ItemAuctionInfoBinding.inflate(
    LayoutInflater.from(context),
    this,
  )

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  fun bind(auction: AuctionEntityItem) {
    binding.tvPlayer.text = auction.cardName
    binding.tvRating.text = auction.startingPrice.toString()
    binding.tvELO.text = auction.currentPrice.toString()
    binding.tvActivity.text = auction.buyoutPrice.toString()
    binding.tvMatches.text = auction.endingOn
  }
}
