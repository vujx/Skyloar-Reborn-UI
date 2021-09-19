package com.example.presentation.ui.auctions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentAuctionBinding
import com.example.presentation.ui.BaseFragment
import com.example.presentation.ui.auctions.adapter.AuctionAdapter
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.presentation.ui.helper.auction.AuctionHelper
import com.example.presentation.ui.helper.auction.AuctionOnClickHelper
import com.example.presentation.ui.helper.auction.ProgressBarHelper
import com.example.presentation.ui.helper.auction.RefreshAuctionsHelper
import com.example.presentation.ui.helper.auction.SearchResultHelper
import com.example.presentation.ui.helper.leaderboards.CallbackPvP
import com.example.util.RangeEditText
import com.example.util.Resource
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuctionFragment : BaseFragment(R.layout.fragment_auction) {

  private lateinit var binding: FragmentAuctionBinding

  private val adapter: AuctionAdapter by inject()
  private val viewModelAuction: AuctionViewModel by viewModel()

  private val progressBarHelper = ProgressBarHelper()
  private val searchResultHelper = SearchResultHelper()
  private val onClickHelper = AuctionOnClickHelper()
  private val auctionHelper = AuctionHelper(null, null, null)
  private val auctionRefreshHelper = RefreshAuctionsHelper()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auction, null, false)
    binding.lifecycleOwner = viewLifecycleOwner

    onPageClickListener = {
      viewModelAuction.getListOfAuctions(
        it,
        20,
        binding.etSearchCardName.text.toString(),
        checkIfInputIsEmpty(binding.etMinPrice.text.toString()),
        checkIfInputIsEmpty(binding.etMaxPrice.text.toString())
      )
    }
    setData()
    setUpRecyclerView()
    bind()

    return binding.root
  }

  private fun bind() {
    viewModelAuction.auctions.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            progressBarHelper.setLoading(false)
            searchResultHelper.setSearchResult("")
            adapter.setListOfAuctions(result.value)
            binding.titleCheck = "1"
            binding.auctionHelper = AuctionHelper(
              binding.etSearchCardName.text.toString(),
              checkIfInputIsEmpty(binding.etMinPrice.text.toString()),
              checkIfInputIsEmpty(binding.etMaxPrice.text.toString())
            )
          }
          is Resource.Failure -> {
            progressBarHelper.setLoading(false)
            searchResultHelper.setSearchResult(getString(R.string.auction_not_found))
            adapter.setListOfAuctions(emptyList())
            displayMessage(result.message)
            binding.titleCheck = ""
            binding.auctionHelper = auctionHelper
          }
          is Resource.Loading -> {
            progressBarHelper.setLoading(true)
            binding.titleCheck = ""
            adapter.setListOfAuctions(emptyList())
          }
          is Resource.Empty -> {
            progressBarHelper.setLoading(false)
            adapter.setListOfAuctions(emptyList())
            searchResultHelper.setSearchResult(getString(R.string.auction_not_found))
            binding.titleCheck = ""
            binding.auctionHelper = auctionHelper
          }
        }
        hideKeyBoard()
      }
    )
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvAuction.layoutManager = LinearLayoutManager(requireContext())
      rvAuction.adapter = adapter
    }
  }

  private fun setData() {
    binding.apply {
      progressBarHlp = progressBarHelper
      searchResult = searchResultHelper
      viewModelAuction = this@AuctionFragment.viewModelAuction
      clickListener = onClickHelper
      etMinPrice.filters = arrayOf(RangeEditText(1, 2147483647))
      etMaxPrice.filters = arrayOf(RangeEditText(1, 2147483647))
      auctionHelper = this@AuctionFragment.auctionHelper
      refresh = auctionRefreshHelper
      callback = CallbackPvP(
        onExportBtnClick = {
          onExportPress(it)
        },
        onPageClick = {
          onPagePress(it)
        }
      )
    }
  }

  private fun hideKeyBoard() {
    requireActivity().currentFocus?.let { view ->
      val imm =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
      imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
  }

  fun checkIfInputIsEmpty(input: String): Int? =
    if (input.isBlank())
      null
    else
      Integer.parseInt(input)
}
