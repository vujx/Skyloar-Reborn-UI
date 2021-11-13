package com.example.presentation.ui.dialogs.searchPvPPlayers

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.Dictionary
import com.example.R
import com.example.databinding.ItemPvePlyerSearchListBinding
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PvPFilterListView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), KoinComponent {

  private val dictionary: Dictionary by inject()
  private val binding = ItemPvePlyerSearchListBinding.inflate(
    LayoutInflater.from(context), this
  )

  fun bind(
    item: PvPPlayerFilterMonthModel,
    onItemClick: (PvPPlayerFilterMonthModel) -> Unit,
  ) {
    binding.pveFilterContentName.text = item.monthName
    checkItem(item.isChecked)
    if (item.isEven % 2 == 0)
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey_lighter))
    else
      binding.root.setBackgroundColor(dictionary.getColorRes(R.color.grey))

    binding.root.setOnClickListener {
      onItemClick(item)
    }
  }

  private fun checkItem(isChecked: Boolean) {
    if (isChecked)
      binding.pvEItemRadioButton.setImageResource(
        R.drawable.ic_baseline_check_circle_24
      )
    else
      binding.pvEItemRadioButton.setImageResource(
        R.drawable.ic_baseline_radio_button_unchecked_24
      )
  }
}
