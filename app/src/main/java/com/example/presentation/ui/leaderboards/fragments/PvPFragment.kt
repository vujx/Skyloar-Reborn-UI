package com.example.presentation.ui.leaderboards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentPvpBinding
import com.example.presentation.MainActivity.Companion.listOfMonth
import com.example.presentation.ui.dialogs.DialogPageListener
import com.example.presentation.ui.helper.auction.ProgressBarHelper
import com.example.presentation.ui.helper.auction.SearchResultHelper
import com.example.presentation.ui.helper.leaderboards.PvPOnClickHelper
import com.example.presentation.ui.leaderboards.adapter.pvp.PvPAdapter
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.util.Resource
import com.example.util.displayMessage
import com.example.util.getMonthValueByName
import com.example.util.getTypePvP
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvPFragment : Fragment(R.layout.fragment_pvp), DialogPageListener {

  private lateinit var binding: FragmentPvpBinding

  private val adapter: PvPAdapter by inject()
  private val viewModelPvP1vs1: PvPPlayerViewModel by viewModel()
  private val leaderboardsViewModel: LeaderboardsViewModel by viewModel()

  private val progressBarHelper = ProgressBarHelper()
  private val searchResultHelper = SearchResultHelper()
  private lateinit var clickListeners: PvPOnClickHelper

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding =
      DataBindingUtil.inflate(inflater, R.layout.fragment_pvp, container, false)
    binding.lifecycleOwner = viewLifecycleOwner

    setUpRecylerView()
    bind()
    setData()
    getMonths()

    return binding.root
  }

  private fun setUpRecylerView() {
    binding.apply {
      rvPvPPlayers.layoutManager = LinearLayoutManager(requireContext())
      rvPvPPlayers.adapter = adapter
    }
  }

  private fun bind() {
    viewModelPvP1vs1.pvpPlayer.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            progressBarHelper.setLoading(false)
            binding.titleCheck = "1"
            if (result.value == null) {
              adapter.setListOfPvPPlayers(emptyList())
              searchResultHelper.setSearchResult(resources.getString(R.string.caching_data))
            } else {
              result.value.let { adapter.setListOfPvPPlayers(it) }
              searchResultHelper.setSearchResult("")
            }
          }
          is Resource.Failure -> {
            progressBarHelper.setLoading(false)
            binding.titleCheck = ""
            searchResultHelper.setSearchResult(resources.getString(R.string.pvp_players_not_found))
            adapter.setListOfPvPPlayers(emptyList())
            displayMessage(result.message, requireContext())
          }
          is Resource.Loading -> {
            progressBarHelper.setLoading(true)
            adapter.setListOfPvPPlayers(emptyList())
          }
          is Resource.Empty -> {
            binding.titleCheck = ""
            progressBarHelper.setLoading(false)
            adapter.setListOfPvPPlayers(emptyList())
            searchResultHelper.setSearchResult(getString(R.string.pvp_players_not_found))
          }
        }
      }
    )
  }

  private fun setData() {
    clickListeners = PvPOnClickHelper(this)
    binding.apply {
      progressBarHlp = progressBarHelper
      searchResult = searchResultHelper
      viewModelPvP1 = viewModelPvP1vs1
      clickListener = clickListeners
    }
  }

  override fun getPageNumber(pageNum: Int) {
    viewModelPvP1vs1.getPvPPlayers(
      getTypePvP(binding.spinnerPlayers.selectedItem.toString()),
      getMonthValueByName(binding.spinner.selectedItem.toString()),
      pageNum,
      20
    )
  }

  private fun getMonths() {
    if (listOfMonth == null) {
      leaderboardsViewModel.getRange("ranges")
      leaderboardsViewModel.listOfRanges.observe(
        viewLifecycleOwner,
        { result ->
          setDataToDropDown(result)
          listOfMonth = result
        }
      )
    } else {
      setDataToDropDown(listOfMonth)
    }
  }

  private fun setDataToDropDown(mapOfMonth: Map<Int, String>?) {
    val arrayAdapter =
      ArrayAdapter(
        requireContext(),
        R.layout.item_drop_down,
        mapOfMonth!!.map {
          it.value
        }
      )
    val arrayAdapterPlayers =
      ArrayAdapter(requireContext(), R.layout.item_drop_down, listOf("1vs1", "2vs2"))
    binding.spinner.adapter = arrayAdapter
    binding.spinnerPlayers.adapter = arrayAdapterPlayers
  }
}
