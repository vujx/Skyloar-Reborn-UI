package com.example.presentation.ui.leaderboards.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentPvP1vs1PlayersBinding
import com.example.presentation.ui.dialogs.DialogPageListener
import com.example.presentation.ui.helper.auction.ProgressBarHelper
import com.example.presentation.ui.helper.auction.SearchResultHelper
import com.example.presentation.ui.helper.leaderboards.PvP1v1OnClickHelper
import com.example.presentation.ui.leaderboards.adapter.PvPAdapter
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.util.Resource
import com.example.util.displayMessage
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvP1vs1PlayersFragment : Fragment(R.layout.fragment_pv_p1vs1_players), DialogPageListener {

    private lateinit var binding: FragmentPvP1vs1PlayersBinding

    private val adapter: PvPAdapter by inject()
    private val viewModelPvP1vs1: PvPPlayerViewModel by viewModel()
    private val viewModelLeaderboards: LeaderboardsViewModel by viewModel()

    private val progressBarHelper = ProgressBarHelper()
    private val searchResultHelper = SearchResultHelper()
    private lateinit var clickListeners: PvP1v1OnClickHelper
    var monthValue = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pv_p1vs1_players, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setUpRecylerView()
        bind()
        setData()

        viewModelLeaderboards.getRange("ranges")
        viewModelPvP1vs1.getPvPPlayers("1v1", 0, 1, 20)
        return binding.root
    }

    private fun setUpRecylerView() {
        binding.apply {
            rvPvPPlayers.layoutManager = LinearLayoutManager(requireContext())
            rvPvPPlayers.adapter = adapter
        }
    }

    private fun bind() {
        viewModelPvP1vs1.pvpPlayer.observe(viewLifecycleOwner, { result ->
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
                    searchResultHelper.setSearchResult("")
                    displayMessage(result.message, requireContext())
                }
                is Resource.Loading -> {
                    progressBarHelper.setLoading(true)
                    binding.titleCheck = ""
                    adapter.setListOfPvPPlayers(emptyList())
                }
                is Resource.Empty -> {
                    binding.titleCheck = ""
                    progressBarHelper.setLoading(false)
                    adapter.setListOfPvPPlayers(emptyList())
                    searchResultHelper.setSearchResult(getString(R.string.pvp_players_not_found))
                }
            }
        })
        viewModelLeaderboards.listOfRanges.observe(viewLifecycleOwner, { result ->
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_drop_down, result.map {
                it.value
            })
            clickListeners = PvP1v1OnClickHelper(this, result)
            binding.apply {
                clickListener = clickListeners
                spinner.adapter = arrayAdapter
                spinner.setSelection(0, false)
                spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        clickListeners.onMonthSelected(
                            viewModelPvP1vs1,
                            binding.tvPage.text.toString(),
                            binding.spinner.getItemAtPosition(position).toString()
                        )
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
            result.forEach {
                if (it.value == binding.spinner.selectedItem.toString())
                    monthValue = it.key
            }
        })
    }

    private fun setData() {
        binding.apply {
            progressBarHlp = progressBarHelper
            searchResult = searchResultHelper
            viewModelPvP1 = viewModelPvP1vs1
        }
    }

    override fun getPageNumber(pageNum: Int) {
        viewModelPvP1vs1.getPvPPlayers("1v1", monthValue, pageNum, 20)
    }
}