package com.example.presentation.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.databinding.PageNavigationBinding

class BottomPageNavigationView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  var onNextPress: ((Unit) -> Unit) = {}
  var onPreviousPress: ((Unit) -> Unit) = {}
  var onPagePress: ((Unit) -> Unit) = {}
  var onExportPress: ((Unit) -> Unit) = {}

  private var binding: PageNavigationBinding =
    PageNavigationBinding.inflate(LayoutInflater.from(context), this)

  init {
    layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
  }

  fun clickListeners() {
    binding.ivForward.setOnClickListener {
      onNextPress(Unit)
    }

    binding.ivBack.setOnClickListener {
      onPreviousPress(Unit)
    }

    binding.tvPage.setOnClickListener {
      onPagePress(Unit)
    }

    binding.ivExportBtn.setOnClickListener {
      onExportPress(Unit)
    }
  }

  fun setSearch(searchCount: String) {
    binding.tvSearchedResults.text = searchCount
  }

  fun setPage(page: String) {
    binding.tvPage.text = page
  }

  fun getPage() = binding.tvPage.text.toString()

  fun getFirstPage(): Int {
    val page = getPage()
    return page.substring(0, page.indexOf(' ')).toInt()
  }
}
