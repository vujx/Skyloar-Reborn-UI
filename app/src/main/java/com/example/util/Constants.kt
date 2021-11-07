package com.example.util

object Constants {

  const val BASE_URL = "https://srahb.bzg.com.hr"
  const val BASE_URL_EXPORT_AUCTIONS = "$BASE_URL/api/auctions/export"

  const val BASE_URL_EXPORT_PVP = "$BASE_URL/api/leaderboards/pvp?type=1v1&month=0&page=10&number=20&export=true"
  const val URL_EXPORT_PVE = "?type=1&players=1&map=67&month=0&page=1&number=20&export=true"
  const val BASE_URL_EXPORT_PVE = "$BASE_URL/api/leaderboards/pve?type=1&players=1&map=67&month=0&page=1&number=20&export=true"
}
