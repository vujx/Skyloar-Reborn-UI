package com.example.presentation.ui.leaderboards.leaderboards

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.databinding.ItemLeaderboardsBinding

class LeaderboardsItem @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

  private val binding: ItemLeaderboardsBinding = ItemLeaderboardsBinding.inflate(
    LayoutInflater.from(context),
    this, true
  )

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  fun bind(item: LeaderBoards) {
    binding.title.text = item.title
    binding.desc.text = item.desc
    binding.image.setImageResource(item.image)
  }
}