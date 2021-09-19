package com.example.presentation.ui.helper.leaderboards

class CallbackPvP(
  private var onExportBtnClick: ((String) -> Unit)?,
  private var onPageClick: ((Int) -> Unit)?
) {

  fun onExportClick(url: String) {
    onExportBtnClick?.invoke(url)
  }

  fun onPageTVClick(page: Int) {
    onPageClick?.invoke(page)
  }
}
