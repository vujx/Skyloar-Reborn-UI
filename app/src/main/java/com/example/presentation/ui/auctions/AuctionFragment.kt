package com.example.presentation.ui.auctions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Dictionary
import com.example.R
import com.example.databinding.FragmentAuctionBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.auctions.adapter.AuctionAdapter
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.util.Constants
import com.example.util.Resource
import com.example.util.visible
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuctionFragment : BaseFragment(R.layout.fragment_auction) {

  private val adapter: AuctionAdapter by inject()
  private val viewModelAuction: AuctionViewModel by viewModel()
  private val dictionary: Dictionary by inject()

  private var _binding: FragmentAuctionBinding? = null
  private val binding get() = _binding!!

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

  private fun clickListeners() {
    onPageClickListener = { page -> getAuctions(page, false) }
    binding.bottomPage.onNextPress = {
      viewModelAuction.onNextPress(binding.bottomPage.getPage(),
        20,
        cardName,
        minPrice,
        maxPrice)
    }
    binding.bottomPage.onPreviousPress = {
      viewModelAuction.onPreviousPress(binding.bottomPage.getPage(),
        20,
        cardName,
        minPrice,
        maxPrice)
    }
    binding.ivSearchBtn.setOnClickListener { getAuctions(1) }
    binding.bottomPage.onPagePress = {
      onPagePress(getLastPage(binding.bottomPage.getPage()),
        getFirstPage(binding.bottomPage.getPage()))
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
            setProgressBarAndSearchResult(visibilityTitles = true)
            adapter.setListOfAuctions(result.value)
          }
          is Resource.Failure -> {
            setProgressBarAndSearchResult()
            adapter.setListOfAuctions(emptyList())
            displayMessage(result.message)
          }
          is Resource.Loading -> {
            setProgressBarAndSearchResult(visibilityProgressBar = true)
            adapter.setListOfAuctions(emptyList())
          }
          is Resource.Empty -> {
            setProgressBarAndSearchResult(
              searchResult = dictionary.getStringRes(R.string.auction_not_found)
            )
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
    searchResult: String = "",
    visibilityTitles: Boolean = false,
  ) {
    binding.prgSearch.showProgressBar(visibilityProgressBar)
    binding.prgSearch.showNoSearchResult(searchResult)
    binding.rootTitles.visible(visibilityTitles)
    binding.swipeRefresh.isRefreshing = false
  }

  private fun getAuctions(page: Int, searchSwipe: Boolean = true) {
    if (searchSwipe) {
      cardName = binding.etSearchCardName.text.toString()
      minPrice = checkIfInputIsEmpty(binding.etMinPrice.text.toString())
      maxPrice = checkIfInputIsEmpty(binding.etMaxPrice.text.toString())
      viewModelAuction.getListOfAuctions(
        page,
        20,
        cardName,
        minPrice,
        maxPrice,
      )
    } else {
      viewModelAuction.getListOfAuctions(
        page,
        20,
        cardName,
        minPrice,
        maxPrice,
      )
    }
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvAuction.layoutManager = LinearLayoutManager(requireContext())
      rvAuction.adapter = adapter
    }
  }
}
