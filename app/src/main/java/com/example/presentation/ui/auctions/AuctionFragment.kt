package com.example.presentation.ui.auctions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.App
import com.example.R
import com.example.databinding.FragmentAuctionBinding
import com.example.presentation.ui.auctions.adapter.AuctionAdapter
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.presentation.ui.auctions.viewmodel.ExportViewModel
import com.example.presentation.ui.helper.AuctionOnClickHelper
import com.example.presentation.ui.helper.ProgressBarHelper
import com.example.presentation.ui.helper.SearchResultHelper
import com.example.util.Resource
import com.example.util.displayMessage
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuctionFragment : Fragment(R.layout.fragment_auction) {

    private lateinit var binding: FragmentAuctionBinding

    private val adapter: AuctionAdapter by inject()
    private val viewModelAuction: AuctionViewModel by viewModel()
    private val viewModelExportFile: ExportViewModel by viewModel()

    private val progressBarHelper = ProgressBarHelper()
    private val searchResultHelper = SearchResultHelper()
    private val onClickHelper = AuctionOnClickHelper()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auction, null, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setData()
        setUpRecyclerView()
        bind()
        viewModelAuction.getListOfAuctions(1, 15, null, null, null)

        return binding.root
    }

    private fun bind() {
        viewModelAuction.auctions.observe(
            viewLifecycleOwner,
            { result ->
                when(result) {
                    is Resource.Success -> {
                        progressBarHelper.setLoading(false)
                        searchResultHelper.setSearchResult("")
                        adapter.setListOfAuctions(result.value)
                    }
                    is Resource.Failure -> {
                        progressBarHelper.setLoading(false)
                        searchResultHelper.setSearchResult("")
                        displayMessage(result.message, requireContext())
                    }
                    is Resource.Loading -> progressBarHelper.setLoading(true)
                    is Resource.Empty -> {
                        progressBarHelper.setLoading(false)
                        adapter.setListOfAuctions(emptyList())
                        searchResultHelper.setSearchResult(getString(R.string.auction_not_found))
                    }
                }
            }
        )

        viewModelExportFile.dsadsaasd.observe(viewLifecycleOwner,
            { result ->
                when(result) {
                    is Resource.Success -> {
                        progressBarHelper.setLoading(false)
                    }
                    is Resource.Failure -> {
                        progressBarHelper.setLoading(false)
                        displayMessage(result.message, requireContext())
                    }
                    is Resource.Loading -> progressBarHelper.setLoading(true)
                    is Resource.Empty -> {
                        progressBarHelper.setLoading(false)
                        displayMessage(App.getStringResource(R.string.unexpected_error), requireContext())
                    }
                }
            })
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
            viewModelExport = viewModelExportFile
            clickListener = onClickHelper
        }
    }
}
