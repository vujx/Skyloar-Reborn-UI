package com.example.presentation.ui.leaderboards.leaderboards

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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

  private val listOfTypes = listOf(
    1,2,4,12,
  )

  private val listOfPvPTypes = listOf("1v1", "2v2")

  var position: Int = 0

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  fun bind(
    item: LeaderBoards,
    position: Int,
    isLeaderBoards: Boolean,
    onClick: ((String) -> Unit)?,
    onLeaderBoardsClick: ((Int) -> Unit)?,
    onLeaderBoardsPvPClick: ((String) -> Unit)?) {
    binding.title.text = item.title
    binding.descTitle.text = item.descTitle
    binding.desc.text = item.desc
    binding.image.setImageResource(item.image)
    Log.d("tusad", onClick.toString())
    Log.d("tusad", onLeaderBoardsClick.toString())
    binding.root.setOnClickListener {
      if (!isLeaderBoards && position < 4) {
        if (onClick != null) {
          onClick(listOfUrl[position])
        }
      } else if(onLeaderBoardsClick != null && position < 4) {
        onLeaderBoardsClick(listOfTypes[position])
      } else if(onLeaderBoardsPvPClick != null && position > 3) {
        onLeaderBoardsPvPClick(listOfPvPTypes[position - 4])
      }
    }
  }
}