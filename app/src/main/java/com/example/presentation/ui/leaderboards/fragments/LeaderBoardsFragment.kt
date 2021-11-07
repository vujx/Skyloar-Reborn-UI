package com.example.presentation.ui.leaderboards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentLeaderBoardsBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.leaderboards.leaderboards.LeaderboardsAdapter
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsPicViewModel
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.Content
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.Loading
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.NavigateToPvePlayers
import com.example.presentation.ui.leaderboards.viewmodel.LeaderBoardsViewState.NavigateToPvpPlayers
import com.example.util.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class LeaderBoardsFragment : BaseFragment(R.layout.fragment_leader_boards) {

  private val viewModel: LeaderBoardsPicViewModel by viewModel()
  private var _binding: FragmentLeaderBoardsBinding? = null
  private val binding get() = _binding!!
  private val adapter = LeaderboardsAdapter()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentLeaderBoardsBinding.inflate(
      inflater, container, false
    )
    viewModel.requestData()
    observeViewModel()
    setUpRecyclerView()
    adapter.onLeaderboardsClick = { type ->
      viewModel.onLeaderBoardsClick(type)
    }
    adapter.onLeaderBoardsPvPClick = { type ->
      viewModel.onLeaderBoardsPvPClick(type)
    }
    adapter.isLeaderboards = true
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun setUpRecyclerView() {
    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter
  }

  private fun observeViewModel() {
    viewModel.viewState.observe(
      viewLifecycleOwner, { data ->
        when(data) {
          is Content -> {
            binding.prgSearch.visible(false)
            adapter.setList(data.data)
          }
          is Loading -> {
            binding.prgSearch.visible(true)
            adapter.setList(emptyList())
          }
          is NavigateToPvePlayers -> {
            navigateTo(
              LeaderBoardsFragmentDirections.actionLeaderboardsFragmentToPvEFragment(data.type)
            )
          }
          is NavigateToPvpPlayers -> {
            navigateTo(
              LeaderBoardsFragmentDirections.actionLeaderboardsFragmentToPvPFragment(data.type)
            )
          }
        }
      }
    )
  }

  private fun navigateTo(directions: NavDirections) {
    NavHostFragment.findNavController(this).navigate(directions)
  }
}