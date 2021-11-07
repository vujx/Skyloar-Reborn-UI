package com.example.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.presentation.ui.dialogs.DialogForAddingPageNumber

open class BaseFragment(
  layoutId: Int,
) : Fragment(layoutId) {

  var onPageClickListener: ((Int) -> Unit)? = null

  var minPrice: Long? = null
  var maxPrice: Long? = null
  var cardName: String? = null

  fun onExportPress(url: String) {
    openBrowser(url)
  }

  fun onPagePress(lastPage: Int, currentPage: Int) {
    val dialog = DialogForAddingPageNumber(lastPage.toLong(), onPageClickListener, currentPage)
    dialog.show(requireActivity().supportFragmentManager, "PageNumberChange")
  }

  fun hideKeyBoard() {
    requireActivity().currentFocus?.let { view ->
      val imm =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
      imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
  }

  fun getFirstPage(page: String) =
    page.substring(0, page.indexOf(' ')).toInt()

  fun getLastPage(page: String) =
    page.substring(getFirstPage(page).toString().length + 3).toInt()

  protected fun openBrowser(url: String) {
    if (Patterns.WEB_URL.matcher(url).matches()) {
      url.let {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse(it))
        requireContext().startActivity(i)
      }
    }
  }
}
