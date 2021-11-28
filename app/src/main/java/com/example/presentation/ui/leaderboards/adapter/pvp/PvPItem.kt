package com.example.presentation.ui.leaderboards.adapter.pvp

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.Dictionary
import com.example.R
import com.example.databinding.ItemPvpPlayerInfoBinding
import com.example.domain.model.PvPPlayer
import com.example.util.numberWithCommas
import com.example.util.visible
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PvPItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr), KoinComponent {

  private val dictionary: Dictionary by inject()
  private val binding: ItemPvpPlayerInfoBinding =
    ItemPvpPlayerInfoBinding.inflate(
      LayoutInflater.from(context),
      this,
    )

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  @SuppressLint("SetTextI18n")
  fun bind(pvpPlayer: PvPPlayer, position: Int) {
    if (position % 2 == 0)
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey_lighter))
    else
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey))
    pvpPlayer.totalMatches?.let {
      binding.tvMatches.visible(true)
      binding.tvMatches.text = it.toString()
    } ?: binding.tvMatches.visible(false)
    binding.tvPlayer.text = pvpPlayer.players.joinToString()
    binding.tvRating.text = numberWithCommas(pvpPlayer.rating)
    binding.tvELO.text = numberWithCommas(pvpPlayer.baseElo)
    binding.tvWins.text = numberWithCommas(pvpPlayer.winsLimited)
    binding.tvLosses.text = numberWithCommas(pvpPlayer.losesLimited)
    binding.tvActivity.text = "${pvpPlayer.activity}%"
  }
}
