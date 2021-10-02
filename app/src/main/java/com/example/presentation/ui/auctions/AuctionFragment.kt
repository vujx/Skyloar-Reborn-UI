package com.example.presentation.ui.auctions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentAuctionBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.auctions.adapter.AuctionAdapter
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.presentation.ui.dialogs.AuctionSearchDialog
import com.example.util.Constants
import com.example.util.Resource
import com.example.util.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuctionFragment : BaseFragment(R.layout.fragment_auction), AuctionSearchDialog.Listener {

  private val adapter: AuctionAdapter by inject()
  private val viewModelAuction: AuctionViewModel by viewModel()

  private var _binding: FragmentAuctionBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    _binding = FragmentAuctionBinding.inflate(inflater, container, false)

    setUpRecyclerView()
    bind()
    clickListeners()
    binding.bottomPage.clickListeners()

    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }

  override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
    menuInflater.inflate(R.menu.toolbar_menu, menu)
    return super.onCreateOptionsMenu(menu, menuInflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.searchIcon -> {
        val dialog = AuctionSearchDialog(this)
        activity?.supportFragmentManager?.let { dialog.show(it, "dsada") }
        true
      }
      else -> true
    }
  }

  private fun clickListeners() {
    onPageClickListener = { page -> getAuctions(page) }
    binding.bottomPage.onNextPress = {
      viewModelAuction.onNextPress(
        binding.bottomPage.getPage(),
        20,
        cardName,
        minPrice,
        maxPrice
      )
    }
    binding.bottomPage.onPreviousPress = {
      viewModelAuction.onPreviousPress(
        binding.bottomPage.getPage(),
        20,
        cardName,
        minPrice,
        maxPrice
      )
    }

    binding.bottomPage.onPagePress = {
      onPagePress(
        getLastPage(binding.bottomPage.getPage()),
        getFirstPage(binding.bottomPage.getPage())
      )
    }
    binding.bottomPage.onExportPress = { onExportPress(Constants.BASE_URL_EXPORT_AUCTIONS) }
    binding.swipeRefresh.setOnRefreshListener { getAuctions(binding.bottomPage.getFirstPage()) }
  }

  @SuppressLint("SetTextI18n")
  private fun bind() {
    viewModelAuction.auctions.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            setProgressBarAndSearchResult(visibilityTitles = true, visibilityBottomPage = true)
            adapter.setListOfAuctions(result.value)
          }
          is Resource.Failure -> {
            setProgressBarAndSearchResult(visibilityErrorView = true)
            adapter.setListOfAuctions(emptyList())
            binding.errorView.onRetryClick = {
              getAuctions(binding.bottomPage.getFirstPage())
            }
            binding.errorView.showError(result.error)
          }
          is Resource.Loading -> {
            setProgressBarAndSearchResult(visibilityProgressBar = true)
            adapter.setListOfAuctions(emptyList())
          }
          is Resource.Empty -> {
            setProgressBarAndSearchResult()
            adapter.setListOfAuctions(emptyList())
          }
        }
        hideKeyBoard()
      }
    )

    viewModelAuction.numOfSearchResult.observe(
      viewLifecycleOwner,
      {
        binding.bottomPage.setSearch("Searched: $it")
      }
    )

    viewModelAuction.pageResult.observe(
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
    visibilityErrorView: Boolean = false
  ) {
    binding.prgSearch.showProgressBar(visibilityProgressBar)
    binding.rootTitles.visible(visibilityTitles)
    binding.swipeRefresh.isRefreshing = false
    binding.bottomPage.visible(visibilityBottomPage)
    if (!visibilityErrorView) binding.errorView.visible(false)
  }

  private fun getAuctions(page: Int) {
    viewModelAuction.getListOfAuctions(
      page,
      20,
      cardName,
      minPrice,
      maxPrice,
    )
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvAuction.layoutManager = LinearLayoutManager(requireContext())
      rvAuction.adapter = adapter
    }
  }

  override fun onSubmit(searchCard: String?, minPrice: Int?, maxPrice: Int?) {
    cardName = searchCard
    this.minPrice = minPrice
    this.maxPrice = maxPrice
    getAuctions(1)
  }
}
