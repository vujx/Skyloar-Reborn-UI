package com.example.presentation.ui.leaderboards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentPveBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.helper.auction.ProgressBarHelper
import com.example.presentation.ui.helper.auction.SearchResultHelper
import com.example.presentation.ui.leaderboards.adapter.pve.PvEAdapter
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvEPlayerViewModel
import com.example.util.Resource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvEFragment : BaseFragment(R.layout.fragment_pve) {

  private lateinit var binding: FragmentPveBinding

  private val viewModelLeaderboards: LeaderboardsViewModel by viewModel()
  private val viewModelPvE: PvEPlayerViewModel by viewModel()

  private val progressBarHelper = ProgressBarHelper()
  private val searchResultHelper = SearchResultHelper()

  private val adapter: PvEAdapter by inject()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_pve, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    viewModelLeaderboards.getRange("difficulties")

    setUpRecyclerView()
    bind()
    setData()

    return binding.root
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
            progressBarHelper.setLoading(false)
            binding.titleCheck = "1"
            if (result.value == null) {
              adapter.setList(emptyList())
              searchResultHelper.setSearchResult(resources.getString(R.string.caching_data))
            } else {
              result.value.let { adapter.setList(it) }
              searchResultHelper.setSearchResult("")
            }
          }
          is Resource.Failure -> {
            progressBarHelper.setLoading(false)
            binding.titleCheck = ""
            searchResultHelper.setSearchResult("")
            displayMessage(result.message)
          }
          is Resource.Loading -> {
            progressBarHelper.setLoading(true)
            binding.titleCheck = ""
            adapter.setList(emptyList())
          }
          is Resource.Empty -> {
            binding.titleCheck = ""
            progressBarHelper.setLoading(false)
            adapter.setList(emptyList())
            searchResultHelper.setSearchResult(getString(R.string.pvp_players_not_found))
          }
        }
      }
    )
  }

  private fun setData() {
    binding.apply {
      progressBarHlp = progressBarHelper
      searchResult = searchResultHelper
    }
  }
}
