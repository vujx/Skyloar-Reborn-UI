package com.example.domain.usecase.home

import com.example.data.repository.leaderboards.DefaultHomeImpl
import com.example.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHomeIntroText(
  private val homeIntroText: DefaultHomeImpl
) {

  suspend operator fun invoke(): Result<String> =
    withContext(Dispatchers.IO) {
      homeIntroText.getIntroHomeTex()
    }
}