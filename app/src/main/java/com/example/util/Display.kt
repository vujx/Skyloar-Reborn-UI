package com.example.util

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.Gravity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.presentation.MainActivity.Companion.listOfMonth
import com.readystatesoftware.chuck.internal.ui.MainActivity

fun displayMessage(message: String, context: Context) {
    val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun checkIfInputIsEmpty(input: String): Int? =
    if (input.isBlank())
        null
    else
        Integer.parseInt(input)

fun exportFile(frg: Fragment, url: String) {
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

    val manager =
        frg.requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    manager.enqueue(request)
}

fun getMonthValueByName(month: String): Int {
    var monthRange = 0
    listOfMonth?.let { result ->
        result.forEach {
            if (it.value == month)
                monthRange = it.key
        }
    }
    return monthRange
}

fun getTypePvP(type: String): String =
    if (type == "1vs1")
        "1v1"
    else
        "2v2"

