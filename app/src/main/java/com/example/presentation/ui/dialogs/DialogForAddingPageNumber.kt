package com.example.presentation.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.App
import com.example.R
import com.example.util.RangeEditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DialogForAddingPageNumber(
    private val listener: DialogPageListener,
    private val maxPage: Int
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_edit_page__num, null)
        setFilters(view)

        return MaterialAlertDialogBuilder(
            requireActivity(),
            R.style.MaterialAlertDialog_OK_color
        ).setView(view)
            .setMessage(App.getStringResource(R.string.page_title))
            .setPositiveButton(App.getStringResource(R.string.ok)) { _, _ ->
                val etPage = view.findViewById<EditText>(R.id.etPage)
                if (etPage.text.toString().isNotBlank())
                    listener.getPageNumber(Integer.parseInt(etPage.text.toString()))
            }.setNegativeButton(App.getStringResource(R.string.cancel)) { _, _ ->
                dialog?.cancel()
            }.create()
    }

    private fun setFilters(view: View) {
        val etDefense = view.findViewById<EditText>(R.id.etPage)
        etDefense.filters = arrayOf(RangeEditText(1, maxPage))
    }
}