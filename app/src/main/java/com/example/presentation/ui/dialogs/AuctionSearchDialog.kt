package com.example.presentation.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.Dictionary
import com.example.R
import com.example.R.style
import com.example.util.RangeEditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class AuctionSearchDialog(private val listener: Listener) : DialogFragment(), KoinComponent {

  private val dictionary: Dictionary by inject()

  override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.dialog_auction_search, null)

    val searchCard = view.findViewById<TextInputEditText>(R.id.etSearchCardName)
    val minPrice = view.findViewById<TextInputEditText>(R.id.etMinPrice)
    val maxPrice = view.findViewById<TextInputEditText>(R.id.etMaxPrice)

    return MaterialAlertDialogBuilder(
      requireActivity(),
      style.MaterialAlertDialog_OK_color
    ).setView(view)
      .setPositiveButton(dictionary.getStringRes(R.string.search)) { _, _ ->
        listener.onSubmit(
          searchCard.text.toString(),
          checkIfInputIsEmpty(minPrice.text.toString()),
          checkIfInputIsEmpty(maxPrice.text.toString())
        )
        dialog?.cancel()
      }.setNegativeButton(dictionary.getStringRes(R.string.cancel)) { _, _ ->
        dialog?.cancel()
      }.create()
  }

  private fun checkIfInputIsEmpty(input: String): Long? =
    if (input.isBlank() || input == "0")
      null
    else
      input.toLong()

  private fun setFilters(view: View) {
    val etDefense = view.findViewById<EditText>(R.id.etPage)
    etDefense.filters = arrayOf(RangeEditText(1, Long.MAX_VALUE))
  }

  interface Listener {

    fun onSubmit(searchCard: String?, minPrice: Long?, maxPrice: Long?)
  }
}
