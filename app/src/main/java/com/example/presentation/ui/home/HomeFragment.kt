package com.example.presentation.ui.home

import android.os.Bundle
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.Dictionary
import com.example.R.string
import com.example.databinding.FragmentHomeBinding
import com.example.presentation.ui.home.viewmodel.HomeViewModel
import com.example.util.Resource
import com.example.util.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.noties.markwon.Markwon

class HomeFragment : Fragment() {

  private val homeViewModel: HomeViewModel by viewModel()
  private val dictionary: Dictionary by inject()

  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)

    homeViewModel.getIntroText()
    bind()

    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun bind() {
    binding.errorView.onRetryClick = {
      homeViewModel.getIntroText()
    }
    homeViewModel.getIntroText.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            binding.errorView.visible(false)
            binding.prgSearch.visible(false)

            binding.tvIntroText.movementMethod = LinkMovementMethod.getInstance()
            binding.tvIntroText.text = Markwon.markdown(requireContext(), result.value)
            binding.tvIntroText.removeLinksUnderline()
          }
          is Resource.Failure -> {
            binding.errorView.showError(result.error, dictionary.getStringRes(string.check_internet))
            binding.prgSearch.visible(false)
          }
          is Resource.Loading -> binding.prgSearch.visible(true)
        }
      }
    )
  }

  private fun TextView.removeLinksUnderline() {
    val spannable = SpannableString(text)
    for (u in spannable.getSpans(0, spannable.length, URLSpan::class.java)) {
      spannable.setSpan(object : URLSpan(u.url) {
        override fun updateDrawState(ds: TextPaint) {
          super.updateDrawState(ds)
          ds.isUnderlineText = false
        }
      }, spannable.getSpanStart(u), spannable.getSpanEnd(u), 0)
    }
    text = spannable
  }
}
