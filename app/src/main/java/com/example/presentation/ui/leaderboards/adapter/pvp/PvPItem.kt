package com.example.presentation.ui.leaderboards.adapter.pvp

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.databinding.ItemPvpPlayerInfoBinding
import com.example.domain.model.PvPPlayer
import com.example.util.visible

class PvPItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

  private val binding: ItemPvpPlayerInfoBinding =
    ItemPvpPlayerInfoBinding.inflate(
      LayoutInflater.from(context),
      this,
    )

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  @SuppressLint("SetTextI18n")
  fun bind(pvpPlayer: PvPPlayer) {
    pvpPlayer.totalMatches?.let {
      binding.tvMatches.text = it.toString()
    } ?: binding.tvMatches.visible(false)
    binding.tvPlayer.text = pvpPlayer.name
    binding.tvRating.text = pvpPlayer.rating.toString()
    binding.tvELO.text = pvpPlayer.baseElo.toString()
    binding.tvWins.text = pvpPlayer.winsLimited.toString()
    binding.tvLosses.text = pvpPlayer.losesLimited.toString()
    binding.tvActivity.text = "${pvpPlayer.activity}%"
  }
}