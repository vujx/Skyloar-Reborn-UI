package com.example.presentation.ui

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.Gravity
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import com.example.presentation.ui.dialogs.DialogForAddingPageNumber
import java.util.jar.Manifest

open class BaseFragment(
  layoutId: Int
) : Fragment(layoutId) {

  private val STORAGE_PERMISSION_CODE: Int = 1000

  var onPageClickListener: ((Int) -> Unit)? = null

  fun displayMessage(message: String) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
  }

  @SuppressLint("WrongConstant") fun onExportPress(url: String) {
   if(checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
     requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
   } else {
      downloadFile(url)
   }
  }

  fun onPagePress(lastPage: Int) {
    val dialog = DialogForAddingPageNumber(lastPage, onPageClickListener)
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
}