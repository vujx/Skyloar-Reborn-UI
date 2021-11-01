package com.example.presentation.ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.R
import com.example.R.string
import com.example.databinding.ErrorHandleBinding
import com.example.domain.error.ErrorEntity
import com.example.domain.error.ErrorEntity.AccessDenied
import com.example.domain.error.ErrorEntity.BackendCaching
import com.example.domain.error.ErrorEntity.Custom
import com.example.domain.error.ErrorEntity.Network
import com.example.domain.error.ErrorEntity.NotFound
import com.example.domain.error.ErrorEntity.ServiceUnavailable
import com.example.domain.error.ErrorEntity.Unknown
import com.example.util.visible
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ErrorView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), KoinComponent {

  private val binding: ErrorHandleBinding =
    ErrorHandleBinding.inflate(LayoutInflater.from(context), this)
  private val dictionary: com.example.Dictionary by inject()
  var onRetryClick: () -> Unit = {}

  init {
    layoutParams =
      LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    binding.root.setBackgroundColor(dictionary.getColorRes(R.color.black))
    orientation = VERTICAL
    hideError()
  }

  fun showError(data: ErrorEntity, notFound: String) {
    binding.root.visible(true)
    binding.btnRetry.visible(true)
    val errorMessage = when (data) {
      Network -> dictionary.getStringRes(string.check_internet)
      NotFound -> dictionary.getStringRes(string.unexpected_error)
      AccessDenied -> dictionary.getStringRes(string.unknown_host)
      ServiceUnavailable -> dictionary.getStringRes(string.unexpected_error)
      Unknown -> {
        binding.btnRetry.visible(false)
        notFound
      }
      is Custom<*> -> dictionary.getStringRes(string.unexpected_error)
      is BackendCaching -> "Backend is curretly caching new data"
    }
    binding.tvErrorMessage.text = errorMessage
    binding.btnRetry.setOnClickListener {
      onRetryClick()
      hideError()
    }
  }

  private fun hideError() {
    binding.root.visible(false)
  }
}
