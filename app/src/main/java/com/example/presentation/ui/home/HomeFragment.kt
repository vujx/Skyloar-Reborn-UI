package com.example.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.Dictionary
import com.example.R.string
import com.example.databinding.FragmentHomeBinding
import com.example.presentation.ui.home.viewmodel.HomeViewModel
import com.example.util.Resource
import com.example.util.visible
import io.noties.markwon.Markwon
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            val markWon = Markwon.create(requireContext())
            markWon.setMarkdown(binding.tvIntroText, result.value)
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
}
