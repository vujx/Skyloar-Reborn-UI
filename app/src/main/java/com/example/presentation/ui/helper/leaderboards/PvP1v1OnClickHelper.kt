package com.example.presentation.ui.helper.leaderboards

import com.example.presentation.ui.dialogs.DialogForAddingPageNumber
import com.example.presentation.ui.leaderboards.fragments.PvP1vs1PlayersFragment
import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.util.Constants
import com.example.util.exportFile
import java.net.URLEncoder

class PvP1v1OnClickHelper(
    private val frg: PvP1vs1PlayersFragment,
    private val listOfMonthRange: Map<Int, String>
) {

    fun onNextBtnPress(viewModel: PvPPlayerViewModel, page: String, monthValue: String) {
        val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
        val lastPage = page.substring(numOfPage.toString().length + 3).toInt()
        val monthRange = monthRange(monthValue)
        if (numOfPage == lastPage)
            viewModel.getPvPPlayers("1v1", monthRange, 1, 20)
        else
            viewModel.getPvPPlayers("1v1", monthRange, numOfPage + 1, 20)
    }

    fun onBackBtnPress(viewModel: PvPPlayerViewModel, page: String, monthValue: String) {
        val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
        val lastPage = page.substring(numOfPage.toString().length + 3).toInt()
        val monthRange = monthRange(monthValue)
        if (numOfPage == 1)
            viewModel.getPvPPlayers("1v1", monthRange, lastPage, 20)
        else
            viewModel.getPvPPlayers("1v1", monthRange, numOfPage - 1, 20)
    }

    private fun monthRange(monthValue: String): Int {
        var monthRange = 0
        listOfMonthRange.forEach {
            if (it.value == monthValue)
                monthRange = it.key
        }
        return monthRange
    }

    fun onPagePress(lastPage: Int) {
        val dialog = DialogForAddingPageNumber(frg, lastPage)
        dialog.show(frg.requireActivity().supportFragmentManager, "PageNumberChange")
    }

    fun onExportPress() {
        exportFile(frg, Constants.BASE_URL_EXPORT_PVP)
    }

    fun onMonthSelected(viewModel: PvPPlayerViewModel, page: String, monthValue: String) {
        val monthRange = monthRange(monthValue)
        val numOfPage = page.substring(0, page.indexOf(' ')).toInt()
        viewModel.getPvPPlayers("1v1", monthRange, numOfPage, 20)
    }
}