package com.example.presentation.ui

import com.example.Dictionary
import com.example.R.string
import com.example.domain.error.ErrorEntity
import com.example.domain.error.ErrorEntity.AccessDenied
import com.example.domain.error.ErrorEntity.Custom
import com.example.domain.error.ErrorEntity.Network
import com.example.domain.error.ErrorEntity.NotFound
import com.example.domain.error.ErrorEntity.ServiceUnavailable
import com.example.domain.error.ErrorEntity.Unknown
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HandleError(
  private val notFoundString: Int
) : KoinComponent {

  private val dictionary: Dictionary by inject()

  fun bind(data: ErrorEntity): String {
    return when (data) {
      Network -> dictionary.getStringRes(string.check_internet)
      NotFound -> dictionary.getStringRes(notFoundString)
      AccessDenied -> dictionary.getStringRes(string.unknown_host)
      ServiceUnavailable -> dictionary.getStringRes(string.unexpected_error)
      Unknown -> dictionary.getStringRes(string.no_internet_connection)
      is Custom<*> -> data.errorModel.toString()
    }
  }
}
