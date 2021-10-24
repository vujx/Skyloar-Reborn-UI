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

  private val listOfUrl = listOf(
    "http://185.203.18.254:7750/api/docs/auctions/",
    "http://185.203.18.254:7750/api/docs/stats/",
    "http://185.203.18.254:7750/api/docs/leaderboards/",
    "http://185.203.18.254:7750/api/docs/info/",
  )

  var position: Int = 0

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  fun bind(item: LeaderBoards, position: Int, onClick: ((String) -> Unit)?) {
    binding.title.text = item.title
    binding.descTitle.text = item.descTitle
    binding.desc.text = item.desc
    binding.image.setImageResource(item.image)
    binding.root.setOnClickListener {
      if (onClick != null && position < 4) {
        onClick(listOfUrl[position])
      }
    }
  }
}