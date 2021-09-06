package com.example.presentation.ui.helper.stat

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.BR
import com.example.presentation.ui.stat.viewmodel.StatViewModel

class RefreshHelper(private val viewModel: StatViewModel) : BaseObservable() {

    @Bindable
    private var isLoading = false

    fun isLoading() = isLoading

    private fun setLoading() {
        isLoading = false
        notifyPropertyChanged(BR.loading)
    }

    fun refreshList() {
        viewModel.getListOfStatValues()
        setLoading()
    }
}
