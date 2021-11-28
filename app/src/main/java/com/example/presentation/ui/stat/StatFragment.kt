package com.example.presentation.ui.stat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Dictionary
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

  private val dictionary: Dictionary by inject()
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

    binding.swipeRefresh.setOnRefreshListener { viewModelStat.getListOfStatValues() }
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
          is Resource.Loading -> {
            setProgressBarAndSearchResult(visibilityProgressBar = true)
            adapter.setListOfStatValues(mutableMapOf())
          }
          is Resource.Failure -> {
            setProgressBarAndSearchResult(visibilityErrorView = true)
            binding.errorView.onRetryClick = {
              viewModelStat.getListOfStatValues()
            }
            adapter.setListOfStatValues(mutableMapOf())
            binding.errorView.showError(result.error, dictionary.getStringRes(R.string.stat_not_found))
          }
          else -> setProgressBarAndSearchResult()
        }
      }
    )
  }

  private fun setProgressBarAndSearchResult(
    visibilityProgressBar: Boolean = false,
    visibilityErrorView: Boolean = false,
    visibilityValue: Boolean = false,
  ) {
    binding.progressBar.visible(visibilityProgressBar)
    if (!visibilityErrorView) binding.errorView.visible(false)
    binding.swipeRefresh.isRefreshing = false
    binding.tvValue.visible(visibilityValue)
    binding.tvStat.visible(visibilityValue)
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvStat.layoutManager = LinearLayoutManager(requireContext())
      rvStat.adapter = adapter
    }
  }
}
