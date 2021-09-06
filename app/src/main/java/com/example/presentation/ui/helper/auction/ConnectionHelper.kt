package com.example.presentation.ui.helper.auction

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.presentation.MainActivity
import com.example.util.ConnectionLiveData
import com.example.BR

class ConnectionHelper(
    private var connectionLiveData: ConnectionLiveData,
    private val activity: MainActivity
) : BaseObservable() {

    private var isLoading = false

    @Bindable
    fun isLoading() = isLoading

    private fun setLoading(loading: Boolean) {
        isLoading = loading
        notifyPropertyChanged(BR.loading)
    }

    fun observeInternetConnection() {
        connectionLiveData = ConnectionLiveData(context = activity.applicationContext)
        connectionLiveData.observe(
            activity,
            { isNetworkAvailable ->
                setLoading(isNetworkAvailable)
            }
        )
    }
}
