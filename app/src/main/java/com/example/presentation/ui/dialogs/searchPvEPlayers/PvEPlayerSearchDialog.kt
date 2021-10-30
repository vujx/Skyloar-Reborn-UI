package com.example.presentation.ui.dialogs.searchPvEPlayers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.databinding.FragmentPvEPlayerSearchDialogBinding
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.Content
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.Loading
import com.example.util.MultiTypeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvEPlayerSearchDialog : Fragment() {

  private val viewModel: PvEFilterViewModel by viewModel()

  private var _binding: FragmentPvEPlayerSearchDialogBinding? = null
  private val binding get() = _binding!!

  private val args: PvEPlayerSearchDialogArgs by navArgs()

  private var adapter: MultiTypeAdapter<PvEPlayerFilterUiModels>? =
    MultiTypeAdapter<PvEPlayerFilterUiModels>(mutableListOf()).apply {
      forItem { item ->
        when (item) {
          is PvEPlayerHeader ->
            showView(R.layout.item_pve_search_header) { _, _ ->
              (this as TextView).text = item.title
            }
          is PvEPlayerType ->
            showView(R.layout.pve_preview_item) { _, _ ->
              (this as PvEFilterListView).bind(item) {
              }
            }
          is PvEPlayerMonth ->
            showView(R.layout.pve_preview_item) { _, _ ->
              (this as PvEFilterListView).bind(item) {
              }
            }
          is PvEPlayerMap ->
            showView(R.layout.pve_preview_item) { _, _ ->
              (this as PvEFilterListView).bind(item) {
              }
            }
          is PvEPlayerButton ->
            showView(R.layout.pve_player_button) { _, _ -> }
          else -> throw NotImplementedError("Unsupported list item type: $item")
        }
      }
    }
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentPvEPlayerSearchDialogBinding.inflate(inflater, container, false)

    initAdapter()
    observeViewModel()
    viewModel.requestData(args.searchParams.maps, args.searchParams.months, args.searchParams.type)
    return binding.root
  }

  override fun onDestroy() {
    super.onDestroy()
    _binding = null
    adapter = null
  }

  private fun initAdapter() {
    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    binding.recyclerView.adapter = adapter
  }

  private fun observeViewModel() {
    viewModel.viewState.observe(
      viewLifecycleOwner, { data ->
        when(data) {
          is Content -> renderContent(data)
          is Loading -> binding.prgSearch.showProgressBar(true)
        }
      }
    )
  }

  private fun renderContent(data: Content) {
    adapter?.update(data.filterList)
    binding.prgSearch.showProgressBar(false)
  }
}