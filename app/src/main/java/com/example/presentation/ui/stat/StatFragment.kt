package com.example.presentation.ui.stat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentStatBinding
import com.example.presentation.ui.helper.auction.ProgressBarHelper
import com.example.presentation.ui.helper.auction.SearchResultHelper
import com.example.presentation.ui.helper.stat.RefreshHelper
import com.example.presentation.ui.stat.adapter.StatAdapter
import com.example.presentation.ui.stat.viewmodel.StatViewModel
import com.example.util.Resource
import com.example.util.displayMessage
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatFragment : Fragment(R.layout.fragment_stat) {

  private lateinit var binding: FragmentStatBinding

  private val adapter: StatAdapter by inject()
  private val progressBarHelper = ProgressBarHelper()
  private val viewModelStat: StatViewModel by viewModel()
  private lateinit var refreshHelper: RefreshHelper
  private val searchResultHelper = SearchResultHelper()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stat, null, false)

    refreshHelper = RefreshHelper(viewModelStat)
    setUpRecyclerView()
    bind()
    setData()

    return binding.root
  }

  private fun bind() {
    viewModelStat.statValues.observe(
      viewLifecycleOwner,
      { result ->
        when (result) {
          is Resource.Success -> {
            progressBarHelper.setLoading(false)
            binding.titleCheck = "1"
            adapter.setListOfStatValues(result.value)
          }
          is Resource.Loading -> progressBarHelper.setLoading(true)
          else -> {
            progressBarHelper.setLoading(false)
            binding.titleCheck = ""
            adapter.setListOfStatValues(emptyList())
            displayMessage(
              getString(R.string.unexpected_error),
              requireContext()
            )
          }
        }
      }
    )
  }

  private fun setUpRecyclerView() {
    binding.apply {
      rvStat.layoutManager = LinearLayoutManager(requireContext())
      rvStat.adapter = adapter
    }
  }

  private fun setData() {
    binding.apply {
      progressBarHlp = progressBarHelper
      refresh = refreshHelper
      searchResult = searchResultHelper
    }
  }
}
