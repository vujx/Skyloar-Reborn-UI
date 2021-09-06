package com.example.presentation.ui.helper.auction

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.presentation.ui.auctions.AuctionFragment
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.presentation.ui.dialogs.DialogForAddingPageNumber
import com.example.util.Constants

class AuctionOnClickHelper(private val frg: AuctionFragment) {

    fun onBackBtnPress(
        viewModelAuction: AuctionViewModel,
        page: String,
        number: Int,
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ) {
        val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
        val lastPage = page.substring(numOfPage.toString().length + 3).toInt()
        if (numOfPage == 1)
            viewModelAuction.getListOfAuctions(lastPage, number, input, minPrice, maxPrice)
        else
            viewModelAuction.getListOfAuctions(numOfPage - 1, number, input, minPrice, maxPrice)
    }

    fun onNextBtnPress(
        viewModelAuction: AuctionViewModel,
        page: String,
        number: Int,
        input: String?,
        minPrice: Int?,
        maxPrice: Int?
    ) {
        val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
        val lastPage = page.substring(numOfPage.toString().length + 3).toInt()

        if (numOfPage == lastPage)
            viewModelAuction.getListOfAuctions(1, number, input, minPrice, maxPrice)
        else
            viewModelAuction.getListOfAuctions(numOfPage + 1, number, input, minPrice, maxPrice)
    }

    fun onPagePress(lastPage: Int) {
        val dialog = DialogForAddingPageNumber(frg, lastPage)
        dialog.show(frg.requireActivity().supportFragmentManager, "PageNumberChange")
    }

    fun onExportPress() {
        val request = DownloadManager.Request(Uri.parse(Constants.BASE_URL_EXPORT_FILE))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Download")
        request.setDescription("The file is downloading...")

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
}
