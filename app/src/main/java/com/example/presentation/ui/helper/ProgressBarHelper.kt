package com.example.presentation.ui.helper

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.BR

class ProgressBarHelper : BaseObservable() {

    private var isLoading = true

    @Bindable
    fun isLoading() = isLoading

    fun setLoading(loading: Boolean) {
        isLoading = loading
        notifyPropertyChanged(BR.loading)
    }
}