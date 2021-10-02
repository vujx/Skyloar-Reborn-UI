package com.example.presentation.ui.stat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentStatBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.stat.adapter.StatAdapter
import com.example.presentation.ui.stat.viewmodel.StatViewModel
import com.example.util.Resource
import com.example.util.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatFragment : BaseFragment(R.layout.fragment_stat) {

  private val adapter: StatAdapter by inject()
  private val viewModelStat: StatViewModel by viewModel()

  private var _binding: FragmentStatBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentStatBinding.inflate(inflater, container, false)

    setUpRecyclerView()
    bind()

    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun bind() {
    viewModelStat.statValues.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            setProgressBarAndSearchResult(visibilityValue = true)
            adapter.setListOfStatValues(result.value)
          }
          is Resource.Loading -> setProgressBarAndSearchResult(visibilityProgressBar = true)
          is Resource.Failure -> {
            setProgressBarAndSearchResult(visibilitySearchRes = true)
            adapter.setListOfStatValues(emptyList())
          }
          else -> {
            setProgressBarAndSearchResult(visibilitySearchRes = true)
            displayMessage(getString(R.string.unexpected_error))
          }
        }
      }
    )
  }

  private fun setProgressBarAndSearchResult(
    visibilityProgressBar: Boolean = false,
    visibilitySearchRes: Boolean = false,
    visibilityValue: Boolean = false,
  ) {
    binding.progressBar.visible(visibilityProgressBar)
    binding.tvSearchNoResult.visible(visibilitySearchRes)
    binding.tvStat.visible(visibilityValue)
    binding.tvValue.visible(visibilityValue)
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvStat.layoutManager = LinearLayoutManager(requireContext())
      rvStat.adapter = adapter
    }
  }
}
