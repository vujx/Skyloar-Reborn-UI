package com.example.presentation.ui.helper

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import com.example.App
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.util.Constants

class AuctionOnClickHelper(
) {

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
        if(numOfPage == 1)
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

        if(numOfPage == lastPage)
            viewModelAuction.getListOfAuctions(1, number, input, minPrice, maxPrice)
        else
            viewModelAuction.getListOfAuctions(numOfPage + 1, number, input, minPrice, maxPrice)
    }

    fun exportFileOfAuctions() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(Constants.BASE_URL_EXPORT_FILE)
        intent.flags = FLAG_ACTIVITY_NEW_TASK
        App.ctx.startActivity(intent)
    }
}