package com.example.domain.repository.home

import com.example.util.Result

interface HomeNetworkDataSource {

  suspend fun getIntroHomeTex(): Result<String>
}