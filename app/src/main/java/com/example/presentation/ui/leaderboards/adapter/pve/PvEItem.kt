package com.example.presentation.ui.leaderboards.adapter.pve

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.Dictionary
import com.example.R
import com.example.databinding.ItemPvePlayersInfoBinding
import com.example.domain.model.PvEPlayer
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PvEItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr), KoinComponent {

  private val dictionary: Dictionary by inject()
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
    position: Int,
  ) {
    if (position % 2 == 0)
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey_lighter))
    else
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey))
    binding.tvPlayers.text = pvePlayer
    binding.tvTime.text = time
    binding.tvDifficulty.text = difficulty
  }
}
