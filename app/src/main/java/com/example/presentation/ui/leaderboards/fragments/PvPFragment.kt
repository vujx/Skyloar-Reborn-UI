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
import com.example.databinding.FragmentPvpBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.dialogs.searchPvPPlayers.PvPPlayerSearchDialog
import com.example.presentation.ui.leaderboards.adapter.pvp.PvPAdapter
import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.util.Constants
import com.example.util.Resource
import com.example.util.visible
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvPFragment : BaseFragment(R.layout.fragment_pvp), PvPPlayerSearchDialog.Listener {

  private val adapter: PvPAdapter by inject()
  private val viewModelPvP: PvPPlayerViewModel by viewModel()

  private val args: PvPFragmentArgs by navArgs()
  private var _binding: FragmentPvpBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
    viewModelPvP.getMonth(args.type)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentPvpBinding.inflate(inflater, container, false)

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
    binding.apply {
      rvPvPPlayers.layoutManager = LinearLayoutManager(requireContext())
      rvPvPPlayers.adapter = adapter
    }
  }

  private fun bind() {
    viewModelPvP.pvpPlayer.observe(
      viewLifecycleOwner,
      { result ->
        when(result) {
          is Resource.Success -> {
            if(result.value == null) adapter.setListOfPvPPlayers(emptyList())
            else {
              result.value.let { adapter.setListOfPvPPlayers(it) }
              if(result.value[0].totalMatches == null) {
                binding.tvMatches.visible(false)
              }
            }
            setProgressBarAndSearchResult(visibilityTitles = true, visibilityBottomPage = true)
          }
          is Resource.Failure -> {
            setProgressBarAndSearchResult(visibilityErrorView = true)
            adapter.setListOfPvPPlayers(emptyList())
            binding.errorView.onRetryClick = {
              if(viewModelPvP.currentMonth == -1) viewModelPvP.getMonth(args.type)
              else getPvPPlayers(viewModelPvP.pageNumber)
            }
            binding.errorView.showError(result.error,
              "Backend is currently caching new data. Please wait a bit until it is done.")
          }
          is Resource.Loading -> {
            setProgressBarAndSearchResult(visibilityProgressBar = true)
            adapter.setListOfPvPPlayers(emptyList())
          }
          is Resource.Empty -> {
            setProgressBarAndSearchResult()
            adapter.setListOfPvPPlayers(emptyList())
          }
        }
      })

    viewModelPvP.numOfSearchResult.observe(
      viewLifecycleOwner,
      {
        binding.bottomPage.setSearch("Searched: $it")
      }
    )

    viewModelPvP.pageResult.observe(
      viewLifecycleOwner,
      {
        binding.bottomPage.setPage(it)
      }
    )
  }

  private fun getPvPPlayers(page: Int) {
    viewModelPvP.getPvPPlayers(
      args.type,
      viewModelPvP.currentMonth,
      page,
      20
    )
  }

  private fun setProgressBarAndSearchResult(
    visibilityProgressBar: Boolean = false,
    visibilityTitles: Boolean = false,
    visibilityBottomPage: Boolean = false,
    visibilityErrorView: Boolean = false,
  ) {
    binding.prgSearch.showProgressBar(visibilityProgressBar)
    binding.rootTitles.visible(visibilityTitles)
    binding.swipeRefresh.isRefreshing = false
    binding.bottomPage.visible(visibilityBottomPage)
    if (!visibilityErrorView) binding.errorView.visible(false)
  }

  private fun onClickListener() {
    onPageClickListener = { page -> getPvPPlayers(page) }

    binding.bottomPage.onNextPress = {
      viewModelPvP.onNextPress(
        args.type,
        viewModelPvP.currentMonth,
        binding.bottomPage.getPage(),
        20
      )
    }

    binding.bottomPage.onPreviousPress = {
      viewModelPvP.onPreviousPress(
        args.type,
        viewModelPvP.currentMonth,
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
      getPvPPlayers(viewModelPvP.pageNumber)
      binding.prgSearch.showProgressBar(false)
    }
  }

  private fun navigateToSearchDialog() {
    if(PvPPlayerViewModel.getMonthList.isNotEmpty()) {
      val dialog = PvPPlayerSearchDialog(
        this, PvPPlayerViewModel.getMonthList, viewModelPvP.currentMonth
      )
      activity?.supportFragmentManager?.let { dialog.show(it, "PVPDialog") }
    } else showMessage()
  }

  private fun showMessage() {
    Snackbar.make(binding.rvPvPPlayers, binding.errorView.getErrorMessage(), Snackbar.LENGTH_SHORT).show()
  }

  override fun onSubmit(selectedMonth: Int) {
    viewModelPvP.getPvPPlayers(args.type, selectedMonth, 1, 20)
  }

  private fun createExportUrl(): String {
    return Constants.BASE_URL + "/api/leaderboards/pvp?type=${args.type}&month=${viewModelPvP.currentMonth}" +
      "&page=${viewModelPvP.pageNumber}&number=20&export=true"
  }
}
