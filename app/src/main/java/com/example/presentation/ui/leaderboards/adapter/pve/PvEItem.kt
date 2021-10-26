package com.example.presentation.ui.leaderboards.adapter.pve

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.databinding.ItemPvePlayersInfoBinding
import com.example.domain.model.PvEPlayer

class PvEItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: ItemPvePlayersInfoBinding = ItemPvePlayersInfoBinding.inflate(
    LayoutInflater.from(context),
    this,
  )

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  fun bind(
    pvePlayer: String,
    time: String,
    difficulty: String,
  ) {
    binding.tvPlayers.text = pvePlayer
    binding.tvTime.text = time
    binding.tvDifficulty.text = difficulty
  }
}
