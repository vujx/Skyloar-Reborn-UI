package com.example.presentation.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.Dictionary
import com.example.R
import com.example.util.RangeEditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent

class DialogForAddingPageNumber(
  private val maxPage: Int,
  private var onClickListener: ((Int) -> Unit)? = null,
  private val currentPage: Int
) : DialogFragment(), KoinComponent {

  private val dictionary: Dictionary by inject()

  override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
    val inflater = requireActivity().layoutInflater
    val view = inflater.inflate(R.layout.dialog_edit_page__num, null)
    setFilters(view)

    return MaterialAlertDialogBuilder(
      requireActivity(),
      R.style.MaterialAlertDialog_OK_color
    ).setView(view)
      .setPositiveButton(dictionary.getStringRes(R.string.ok)) { _, _ ->
        val etPage = view.findViewById<EditText>(R.id.etPage)
        if (etPage.text.toString().isNotBlank() || etPage.text.toString() == currentPage.toString()) onClickListener?.invoke(Integer.parseInt(etPage.text.toString()))
      }.setNegativeButton(dictionary.getStringRes(R.string.cancel)) { _, _ ->
        dialog?.cancel()
      }.create()
  }

  private fun setFilters(view: View) {
    val etDefense = view.findViewById<EditText>(R.id.etPage)
    etDefense.filters = arrayOf(RangeEditText(1, maxPage))
  }
}
