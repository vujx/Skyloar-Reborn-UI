package com.example.presentation.ui.leaderboards.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Dictionary
import com.example.R
import com.example.databinding.FragmentPvpBinding
import com.example.presentation.MainActivity.Companion.listOfMonth
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.leaderboards.adapter.pvp.PvPAdapter
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.util.Constants
import com.example.util.Resource
import com.example.util.getMonthValueByName
import com.example.util.getTypePvP
import com.example.util.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvPFragment : BaseFragment(R.layout.fragment_pvp) {

  private val adapter: PvPAdapter by inject()
  private val viewModelPvP: PvPPlayerViewModel by viewModel()
  private val leaderboardsViewModel: LeaderboardsViewModel by viewModel()
  private val dictionary: Dictionary by inject()

  private var _binding: FragmentPvpBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentPvpBinding.inflate(inflater, container, false)

    setUpRecyclerView()
    bind()
    getMonths()
    clickListeners()

    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvPvPPlayers.layoutManager = LinearLayoutManager(requireContext())
      rvPvPPlayers.adapter = adapter
    }
  }

  @SuppressLint("SetTextI18n")
  private fun bind() {
    viewModelPvP.pvpPlayer.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            if (result.value == null) {
              adapter.setListOfPvPPlayers(emptyList())
              setProgressBarAndSearchResult(searchResult = resources.getString(R.string.caching_data))
            } else {
              if (getTypePvP(binding.spinnerPlayers.selectedItem.toString()) == "2v2")
                binding.tvMatches.visible(false)
              else binding.tvMatches.visible(true)
              result.value.let { adapter.setListOfPvPPlayers(it) }
              setProgressBarAndSearchResult(visibilityTitles = true)
            }
          }
          is Resource.Failure -> {
            setProgressBarAndSearchResult(
              searchResult = dictionary.getStringRes(R.string.pvp_players_not_found)
            )
            adapter.setListOfPvPPlayers(emptyList())
            displayMessage(result.message)
          }
          is Resource.Loading -> {
            setProgressBarAndSearchResult(visibilityProgressBar = true)
            adapter.setListOfPvPPlayers(emptyList())
          }
          is Resource.Empty -> {
            setProgressBarAndSearchResult(
              searchResult = dictionary.getStringRes(R.string.pvp_players_not_found)
            )
            adapter.setListOfPvPPlayers(emptyList())
          }
        }
        hideKeyBoard()
      }
    )

    viewModelPvP.numOfSearchResult.observe(
      viewLifecycleOwner,
      {
        binding.tvSearchedResults.text = "Searched: $it"
      }
    )

    viewModelPvP.pageResult.observe(
      viewLifecycleOwner,
      {
        binding.tvPage.text = it
      }
    )
  }

  private fun clickListeners() {
    onPageClickListener = {
      getPvPPlayers(it)
    }

    binding.ivSearchBtn.setOnClickListener {
      getPvPPlayers(1)
    }

    binding.ivBack.setOnClickListener {
      if (binding.tvPage.text.toString() != "1 / 1") {
        if (getFirstPage(binding.tvPage.text.toString()) == 1) {
          getPvPPlayers(getLastPage(binding.tvPage.text.toString()))
        } else {
          getPvPPlayers(getFirstPage(binding.tvPage.text.toString()) - 1)
        }
      }
    }

    binding.ivForward.setOnClickListener {
      if (binding.tvPage.text.toString() != "1 / 1") {
        if (getFirstPage(binding.tvPage.text.toString()) == getLastPage(binding.tvPage.text.toString())) {
          getPvPPlayers(1)
        } else getPvPPlayers(getFirstPage(binding.tvPage.text.toString()) + 1)
      }
    }

    binding.tvPage.setOnClickListener {
      onPagePress(
        getLastPage(binding.tvPage.text.toString()),
        getFirstPage(binding.tvPage.text.toString()),
      )
    }

    binding.ivExportBtn.setOnClickListener {
      onExportPress(Constants.BASE_URL_EXPORT_PVP)
    }
  }

  private fun getPvPPlayers(page: Int) {
    viewModelPvP.getPvPPlayers(
      getTypePvP(binding.spinnerPlayers.selectedItem.toString()),
      getMonthValueByName(binding.spinner.selectedItem.toString()),
      page,
      20,
    )
  }

  private fun setProgressBarAndSearchResult(
    visibilityProgressBar: Boolean = false,
    searchResult: String = "",
    visibilityTitles: Boolean = false,
  ) {
    binding.progressBar.visible(visibilityProgressBar)
    binding.tvSearchNoResult.text = searchResult
    binding.rootTitles.visible(visibilityTitles)
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
