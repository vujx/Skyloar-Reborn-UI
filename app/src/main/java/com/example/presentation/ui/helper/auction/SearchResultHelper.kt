package com.example.presentation.ui.helper.auction

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.BR

class SearchResultHelper : BaseObservable() {

    private var searchResult: String = ""

    @Bindable
    fun getSearchResult() = searchResult

    fun setSearchResult(searchResult: String) {
        this.searchResult = searchResult
        notifyPropertyChanged(BR.searchResult)
    }
}
