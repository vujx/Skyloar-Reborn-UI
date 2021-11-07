package com.example.presentation.ui.leaderboards.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentPveBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEPlayerSearchDialog
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvESearchResult
import com.example.presentation.ui.leaderboards.adapter.pve.PvEAdapter
import com.example.presentation.ui.leaderboards.viewmodel.PvEPlayerViewModel
import com.example.util.Constants
import com.example.util.Resource
import com.example.util.visible
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvEFragment : BaseFragment(R.layout.fragment_pve), PvEPlayerSearchDialog.Listener {

  private val viewModelPvE: PvEPlayerViewModel by viewModel()
  private val adapter: PvEAdapter by inject()

  private var _binding: FragmentPveBinding? = null
  private val binding get() = _binding!!

  private val args: PvEFragmentArgs by navArgs()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    viewModelPvE.getMaps(args.type)
    viewModelPvE.currentPLayers = args.type
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentPveBinding.inflate(inflater, container, false)

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
      android.R.id.home -> {
        findNavController().popBackStack()
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
            binding.errorView.onRetryClick = {
              if(viewModelPvE.currentMap == 0) viewModelPvE.getMaps(args.type)
              else getPvEPlayers(viewModelPvE.pageNumber)
            }
            binding.errorView.showError(result.error,
              "Backend is currently caching new data. Please wait a bit until it is done.")
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
        viewModelPvE.currentPLayers,
        viewModelPvE.currentMap,
        viewModelPvE.currentMonth,
        binding.bottomPage.getPage(),
        20
      )
    }

    binding.bottomPage.onPreviousPress = {
      viewModelPvE.onPreviousPress(
        args.type,
        viewModelPvE.currentPLayers,
        viewModelPvE.currentMap,
        viewModelPvE.currentMonth,
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
    binding.bottomPage.onExportPress = { onExportPress(createExportUrl()) }
    binding.swipeRefresh.setOnRefreshListener {
      getPvEPlayers(viewModelPvE.pageNumber)
      binding.prgSearch.showProgressBar(false)
    }
  }

  private fun getPvEPlayers(page: Int) {
    viewModelPvE.getPvEPlayers(
      args.type,
      viewModelPvE.currentPLayers,
      viewModelPvE.currentMap,
      viewModelPvE.currentMonth,
      page,
      20
    )
  }

  private fun navigateToSearchDialog() {
    if(PvEPlayerViewModel.getMapList.isNotEmpty() && PvEPlayerViewModel.getMonthList.isNotEmpty()) {
      val dialog = PvEPlayerSearchDialog(
        this,
        PvEPlayerViewModel.getMapList,
        PvEPlayerViewModel.getMonthList,
        args.type,
        viewModelPvE.currentPLayers,
        viewModelPvE.currentMonth,
        viewModelPvE.currentMap
      )
      activity?.supportFragmentManager?.let { dialog.show(it, "dsada")
      }
    } else {
      showMessage()
    }
  }

  private fun showMessage() {
    Snackbar.make(binding.rvPvEPlayers, binding.errorView.getErrorMessage(), Snackbar.LENGTH_SHORT).show()
  }

  override fun onSubmit(searchResult: PvESearchResult) {
    viewModelPvE.currentMap = searchResult.map
    viewModelPvE.currentMonth = searchResult.month
    if(searchResult.type != 0) viewModelPvE.currentPLayers = searchResult.type
    getPvEPlayers(1)
  }

  private fun createExportUrl(): String {
    return Constants.BASE_URL + "/api/leaderboards/pve?type=${args.type}&players=${viewModelPvE.currentPLayers}&map=${viewModelPvE.currentMap}&month=${viewModelPvE.currentMonth}&page=${viewModelPvE.pageNumber}&number=20&export=true"
  }
}
