package com.example.presentation.ui.dialogs.searchPvEPlayers

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Dictionary
import com.example.R
import com.example.R.style
import com.example.presentation.ui.dialogs.searchPvEPlayers.PvEFilterViewState.Content
import com.example.util.MultiTypeAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class PvEPlayerSearchDialog(
  private var listener: Listener,
  private val map: MutableMap<Int, String>,
  private val month: MutableMap<Int, String>,
  private val type: Int,
  private val realType: Int,
  private val selectedMonth: Int,
  private val selectedMap: Int,
) : DialogFragment(), KoinComponent {

  private val viewModel: PvEFilterViewModel by viewModel()
  private val dictionary: Dictionary by inject()

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
                viewModel.onItemClick(item)
              }
            }
          is PvEPlayerMonth ->
            showView(R.layout.pve_preview_item) { _, _ ->
              (this as PvEFilterListView).bind(item) {
                viewModel.onItemClick(item)
              }
            }
          is PvEPlayerMap ->
            showView(R.layout.pve_preview_item) { _, _ ->
              (this as PvEFilterListView).bind(item) {
                viewModel.onItemClick(item)
              }
            }
          else -> throw NotImplementedError("Unsupported list item type: $item")
        }
      }
    }

  override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.fragment_pv_e_player_search_dialog, null)

    observeViewModel()
    viewModel.requestData(map, month, type, realType, selectedMonth, selectedMap)
    val rvListOfSearchResult = view.findViewById<RecyclerView>(R.id.recyclerView)
    initAdapter(rvListOfSearchResult)

    return MaterialAlertDialogBuilder(
      requireActivity(),
      style.MaterialAlertDialog_OK_color
    ).setView(view)
      .setPositiveButton(dictionary.getStringRes(R.string.search)) { _, _ ->
        listener.onSubmit(
          viewModel.onSearchClick(map, month)
        )
        dialog?.cancel()
      }.setNegativeButton(dictionary.getStringRes(R.string.cancel)) { _, _ ->
        dialog?.cancel()
      }.create()
  }

  override fun onDestroy() {
    super.onDestroy()
    adapter = null
  }

  private fun initAdapter(rv: RecyclerView) {
    rv.layoutManager = LinearLayoutManager(requireContext())
    rv.adapter = adapter
  }

  private fun observeViewModel() {
    viewModel.viewState.observe(
      requireActivity(),
      { data ->
        when (data) {
          is Content -> renderContent(data)
        }
      }
    )
  }

  private fun renderContent(data: Content) {
    adapter?.update(data.filterList)
  }

  interface Listener {

    fun onSubmit(searchResult: PvESearchResult)
  }
}
