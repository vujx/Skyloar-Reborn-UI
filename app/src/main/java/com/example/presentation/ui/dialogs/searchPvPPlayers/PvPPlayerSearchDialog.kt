package com.example.presentation.ui.dialogs.searchPvPPlayers

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Dictionary
import com.example.R
import com.example.R.style
import com.example.presentation.ui.dialogs.searchPvPPlayers.PvPFilterViewState.Content
import com.example.util.MultiTypeAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PvPPlayerSearchDialog(
  private var listener: Listener,
  private val month: MutableMap<Int, String>,
  private val selectedMonth: Int,
) : DialogFragment() {

  private val viewModel: PvPSearchViewModel by viewModel()
  private val dictionary: Dictionary by inject()

  private var adapter: MultiTypeAdapter<PvPPlayerFilterUiModels>? =
    MultiTypeAdapter<PvPPlayerFilterUiModels>(mutableListOf()).apply {
      forItem { item ->
        when(item) {
          is HeaderPvP -> {
            showView(R.layout.item_pve_search_header) { _, _ ->
              (this as TextView).text = item.title
            }
          }
          is PvPPlayerFilterMonthModel -> {
            showView(R.layout.pvp_filter_item_preview) { _, _ ->
              (this as PvPFilterListView).bind(item) {
                viewModel.onItemClick(it)
              }
            }
          }
          else -> throw NotImplementedError("Unsupported list item type: $item")
        }
      }
    }
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.dialog_pvp_search, null)

    val rvPvPSearch = view.findViewById<RecyclerView>(R.id.rvPvPPlayers)
    initAdapter(rvPvPSearch)

    observeViewModel()
    viewModel.requestData(month, selectedMonth)


    return MaterialAlertDialogBuilder(
      requireActivity(),
      style.MaterialAlertDialog_OK_color
    ).setView(view)
      .setPositiveButton(dictionary.getStringRes(R.string.search)) { _, _ ->
        listener.onSubmit(
          viewModel.onSearchClick(month)
        )
        dialog?.cancel()
      }.setNegativeButton(dictionary.getStringRes(R.string.cancel)) { _, _ ->
        dialog?.cancel()
      }.create()
  }

  private fun initAdapter(rv: RecyclerView) {
    rv.layoutManager = LinearLayoutManager(requireContext())
    rv.adapter = adapter
  }

  private fun renderContent(data: Content) {
    adapter?.update(data.filterList)
  }

  private fun observeViewModel() {
    viewModel.viewState.observe(
      requireActivity(), { data ->
        when(data) {
          is Content -> renderContent(data)
        }
      }
    )
  }

  interface Listener {

    fun onSubmit(selectedMonth: Int)
  }
}