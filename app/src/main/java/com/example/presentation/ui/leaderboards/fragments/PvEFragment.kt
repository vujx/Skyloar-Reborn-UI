package com.example.presentation.ui.leaderboards.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Dictionary
import com.example.R
import com.example.databinding.FragmentPveBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvESearch
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvESearchResult
import com.example.presentation.ui.leaderboards.adapter.pve.PvEAdapter
import com.example.presentation.ui.leaderboards.viewmodel.PvEPlayerViewModel
import com.example.util.Constants
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

  private val args: PvEFragmentArgs by navArgs()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentPveBinding.inflate(inflater, container, false)

    arguments?.getParcelable<PvESearchResult>("searchResult")?.let {
      if(it.type == 0) {
        viewModelPvE.getPvEPlayers(
          args.type, args.type, it.map, it.month, 1, 20
        )
      } else {
        viewModelPvE.getPvEPlayers(
          args.type, it.type, it.map, it.month, 1, 20
        )
      }
    } ?: viewModelPvE.getMaps(args.type)
    setUpRecyclerView()
    bind()
    onClickListener()
    binding.bottomPage.clickListeners()

    return binding.root
  }

  override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    menuInflater.inflate(R.menu.toolbar_menu, menu)
    return super.onCreateOptionsMenu(menu, menuInflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.searchIcon -> {
        navigateToSearchDialog()
        true
      }
      else -> true
    }
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
            binding.errorView.showError(result.error,
              dictionary.getStringRes(R.string.pvp_players_not_found))
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

    viewModelPvE.numOfSearchResult.observe(
      viewLifecycleOwner,
      {
        binding.bottomPage.setSearch("Searched: $it")
      }
    )

    viewModelPvE.pageResult.observe(
      viewLifecycleOwner,
      {
        binding.bottomPage.setPage(it)
      }
    )
  }

  private fun setProgressBarAndSearchResult(
    visibilityProgressBar: Boolean = false,
    visibilityTitles: Boolean = false,
    visibilityBottomPage: Boolean = false,
    visibilityErrorView: Boolean = false,
  ) {
    binding.prgSearch.showProgressBar(visibilityProgressBar)
    binding.tvPlayers.visible(visibilityTitles)
    binding.tvDifficulty.visible(visibilityTitles)
    binding.tvTime.visible(visibilityTitles)
    binding.swipeRefresh.isRefreshing = false
    binding.bottomPage.visible(visibilityBottomPage)
    if (!visibilityErrorView) binding.errorView.visible(false)
  }

  private fun onClickListener() {
    onPageClickListener = { page -> getPvEPlayers(page) }

    binding.bottomPage.onNextPress = {
      viewModelPvE.onNextPress(
        args.type,
        args.type,
        PvEPlayerViewModel.getMapList.keys.first(),
        0,
        binding.bottomPage.getPage(),
        20
      )
    }

    binding.bottomPage.onPreviousPress = {
      viewModelPvE.onPreviousPress(
        args.type,
        args.type,
        PvEPlayerViewModel.getMapList.keys.first(),
        0,
        binding.bottomPage.getPage(),
        20
      )
    }

    binding.bottomPage.onPagePress = {
      onPagePress(
        getLastPage(binding.bottomPage.getPage()),
        getFirstPage(binding.bottomPage.getPage())
      )
    }
    binding.bottomPage.onExportPress = { onExportPress(Constants.BASE_URL_EXPORT_AUCTIONS) }
    binding.swipeRefresh.setOnRefreshListener {
      getPvEPlayers(binding.bottomPage.getFirstPage())
      binding.prgSearch.showProgressBar(false)
    }
  }

  private fun getPvEPlayers(page: Int) {
    viewModelPvE.getPvEPlayers(
      args.type,
      1,
      PvEPlayerViewModel.getMapList.keys.first(),
      0,
      page,
      20
    )
  }

  private fun navigateToSearchDialog() {
    Log.d("ipsi123", viewModelPvE.currentPLayers.toString())
    navigateTo(PvEFragmentDirections.actionPvEFragmentToPvEPlayerSearchDialog(
      PvESearch(
        PvEPlayerViewModel.getMapList,
        PvEPlayerViewModel.getMonthList,
        args.type,
        viewModelPvE.currentPLayers,
        viewModelPvE.currentMonth,
        viewModelPvE.currentMap
      )
    ))
  }

  private fun navigateTo(directions: NavDirections) {
    NavHostFragment.findNavController(this).navigate(directions)
  }
}
