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
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvEPlayerViewModel
import com.example.util.Resource
import com.example.util.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvEFragment : BaseFragment(R.layout.fragment_pve) {

  private val viewModelLeaderboards: LeaderboardsViewModel by viewModel()
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
    viewModelLeaderboards.getRange("difficulties")

    setUpRecyclerView()
    bind()

    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvPVEPlayers.layoutManager = LinearLayoutManager(requireContext())
      rvPVEPlayers.adapter = adapter
    }
  }

  private fun bind() {
    viewModelPvE.pvePlayer.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            if (result.value == null) {
              adapter.setList(emptyList())
              setProgressBarAndSearchResult(searchResult = dictionary.getStringRes(R.string.caching_data))
            } else {
              result.value.let { adapter.setList(it) }
              setProgressBarAndSearchResult()
            }
          }
          is Resource.Failure -> {
            setProgressBarAndSearchResult()
            displayMessage(result.message)
          }
          is Resource.Loading -> {
            setProgressBarAndSearchResult(visibilityProgressBar = true)
            adapter.setList(emptyList())
          }
          is Resource.Empty -> {
            setProgressBarAndSearchResult(searchResult = dictionary.getStringRes(R.string.pvp_players_not_found))
            adapter.setList(emptyList())
          }
        }
      }
    )
  }

  private fun setProgressBarAndSearchResult(
    visibilityProgressBar: Boolean = false,
    searchResult: String = "",
  ) {
    binding.progressBar.visible(visibilityProgressBar)
    binding.tvSearchNoResult.text = searchResult
  }
}
