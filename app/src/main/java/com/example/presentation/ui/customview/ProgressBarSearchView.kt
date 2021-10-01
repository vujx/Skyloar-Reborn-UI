package com.example.presentation.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.Dictionary
import com.example.databinding.ProgressSearchResultViewBinding
import com.example.util.visible
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProgressBarSearchView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), KoinComponent {

  private val resources: Dictionary by inject()

  private val binding: ProgressSearchResultViewBinding =
    ProgressSearchResultViewBinding.inflate(LayoutInflater.from(context), this)

  fun showProgressBar(visibility: Boolean) {
    binding.progressBar.visible(visibility)
  }

  fun showNoSearchResult(message: String) {
    binding.tvSearchedResults.text = message
  }
}