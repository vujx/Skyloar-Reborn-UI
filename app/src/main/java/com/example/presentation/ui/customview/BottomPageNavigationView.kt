package com.example.presentation.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.Dictionary
import com.example.databinding.PageNavigationBinding
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BottomPageNavigationView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), KoinComponent {

  private val resources: Dictionary by inject()

  private val binding: PageNavigationBinding =
    PageNavigationBinding.inflate(LayoutInflater.from(context), this)

  fun nextPageAction(
    action: ((Unit) -> Unit)
  ) {
    binding.ivForward.setOnClickListener {
      if (binding.tvPage.text.toString() != "1 / 1") {
        if (getFirstPage(binding.tvPage.text.toString()) == getLastPage(binding.tvPage.text.toString())) {

        } else getAuctions(getFirstPage(binding.tvPage.text.toString()) + 1)
      }
    }
  }

  private fun getAuctions(page: Int) {

  }
  private fun getFirstPage(page: String) =
    page.substring(0, page.indexOf(' ')).toInt()

  private fun getLastPage(page: String) =
    page.substring(getFirstPage(page).toString().length + 3).toInt()
}