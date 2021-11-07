package com.example.presentation.ui

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.presentation.ui.dialogs.DialogForAddingPageNumber
import com.example.util.Constants
import com.example.util.Constants.BASE_URL_EXPORT_PVE
import java.net.URLEncoder

open class BaseFragment(
  layoutId: Int,
) : Fragment(layoutId) {

  private val STORAGE_PERMISSION_CODE: Int = 1000
  var onPageClickListener: ((Int) -> Unit)? = null

  var minPrice: Long? = null
  var maxPrice: Long? = null
  var cardName: String? = null

  @SuppressLint("WrongConstant")
  fun onExportPress(url: String) {
    val myUrl = Constants.BASE_URL + URLEncoder.encode(Constants.URL_EXPORT_PVE, "utf-8")
    Log.d("ispis", myUrl)
    Log.d("ispisovo", Uri.parse(BASE_URL_EXPORT_PVE).toString())

    val request = DownloadManager.Request(myUrl.toUri())
      .setTitle("File")
      .setDescription("Downloading...")
      .setMimeType("application/vnd.ms-excel")
      .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
      .setAllowedOverMetered(true)

    val dm = requireActivity().getSystemService(DOWNLOAD_SERVICE) as DownloadManager

    dm.enqueue(request)
  }

  fun onPagePress(lastPage: Int, currentPage: Int) {
    val dialog = DialogForAddingPageNumber(lastPage.toLong(), onPageClickListener, currentPage)
    dialog.show(requireActivity().supportFragmentManager, "PageNumberChange")
  }

  private fun downloadFile(url: String) {
    val request = DownloadManager.Request(Uri.parse(url))
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
    request.setTitle("Download")
    request.setDescription("The file is downloading...")
    request.setMimeType("text/csv")
    request.allowScanningByMediaScanner()
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(
      Environment.DIRECTORY_DOWNLOADS,
      "${System.currentTimeMillis()}"
    )

    val manager = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
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
