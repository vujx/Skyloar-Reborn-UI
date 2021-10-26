package com.example.presentation.ui.leaderboards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Dictionary
import com.example.R
import com.example.databinding.FragmentPveBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.leaderboards.adapter.pve.PvEAdapter
import com.example.presentation.ui.leaderboards.viewmodel.PvEPlayerViewModel
import com.example.util.Resource
import com.example.util.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvEFragment : BaseFragment(R.layout.fragment_pve) {

  private val viewModelPvE: PvEPlayerViewModel by viewModel()
  private val adapter: PvEAdapter by inject()
  private val dictionary: Dictionary by inject()

  private var _binding: FragmentPveBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentPveBinding.inflate(inflater, container, false)

    setUpRecyclerView()
    bind()

    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun setUpRecyclerView() {
    binding.rvPvEPlayers.layoutManager = LinearLayoutManager(requireContext())
    binding.rvPvEPlayers.adapter = adapter
  }

  private fun bind() {
    viewModelPvE.pvePlayer.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            if (result.value == null) adapter.setList(emptyList())
            else result.value.let { adapter.setList(it) }
            setProgressBarAndSearchResult(visibilityTitles = true, visibilityBottomPage = true)
          }
          is Resource.Failure -> {
            setProgressBarAndSearchResult(visibilityErrorView = true)
            adapter.setList(emptyList())
            binding.errorView.showError(result.error, dictionary.getStringRes(R.string.pvp_players_not_found))
          }
          is Resource.Loading -> {
            setProgressBarAndSearchResult(visibilityProgressBar = true)
            adapter.setList(emptyList())
          }
          is Resource.Empty -> {
            setProgressBarAndSearchResult()
            adapter.setList(emptyList())
          }
        }
        hideKeyBoard()
      })
  }

  private fun setProgressBarAndSearchResult(
    visibilityProgressBar: Boolean = false,
    visibilityTitles: Boolean = false,
    visibilityBottomPage: Boolean = false,
    visibilityErrorView: Boolean = false
  ) {
    binding.prgSearch.showProgressBar(visibilityProgressBar)
    binding.tvPlayers.visible(visibilityTitles)
    binding.tvDifficulty.visible(visibilityTitles)
    binding.tvTime.visible(visibilityTitles)
    binding.swipeRefresh.isRefreshing = false
    binding.bottomPage.visible(visibilityBottomPage)
    if (!visibilityErrorView) binding.errorView.visible(false)
  }
}
