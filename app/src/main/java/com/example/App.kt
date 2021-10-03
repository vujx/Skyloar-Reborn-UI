package com.example

import android.app.Application
import com.example.data.di.ApiServiceModule.provideAuctionService
import com.example.data.di.ApiServiceModule.provideHomeIntroService
import com.example.data.di.ApiServiceModule.provideHttpClient
import com.example.data.di.ApiServiceModule.provideLeaderboardService
import com.example.data.di.ApiServiceModule.provideRetrofit
import com.example.data.mapper.PvEPlayerMapper
import com.example.data.mapper.PvPPlayerMapper
import com.example.data.network.NetworkResponseHelper
import com.example.data.repository.auction.DefaultAuctionRepository
import com.example.data.repository.leaderboards.DefaultHomeImpl
import com.example.data.repository.leaderboards.DefaultLeaderboardsRepository
import com.example.data.repository.leaderboards.DefaultPvERepository
import com.example.data.repository.leaderboards.DefaultPvPRepository
import com.example.data.repository.stat.DefaultStatRepository
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.repository.home.HomeNetworkDataSource
import com.example.domain.repository.leaderboard.LeaderboardRepository
import com.example.domain.repository.leaderboard.pve.PvERepository
import com.example.domain.repository.leaderboard.pvp.PvPRepository
import com.example.domain.repository.stat.StatRepository
import com.example.domain.usecase.auction.GetListOfAuctions
import com.example.domain.usecase.auction.GetNumberOfSearchResults
import com.example.domain.usecase.home.GetHomeIntroText
import com.example.domain.usecase.leaderboards.GetMaps
import com.example.domain.usecase.leaderboards.GetRanges
import com.example.domain.usecase.leaderboards.pve.GetNumOfPvESearchResult
import com.example.domain.usecase.leaderboards.pve.GetPvEPlayers
import com.example.domain.usecase.leaderboards.pvp.GetNumOfPvPSearchResult
import com.example.domain.usecase.leaderboards.pvp.GetPvPPlayers
import com.example.domain.usecase.stat.GetStatValues
import com.example.presentation.ui.auctions.adapter.AuctionAdapter
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.presentation.ui.home.viewmodel.HomeViewModel
import com.example.presentation.ui.leaderboards.adapter.pve.PvEAdapter
import com.example.presentation.ui.leaderboards.adapter.pvp.PvPAdapter
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvEPlayerViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.presentation.ui.stat.adapter.StatAdapter
import com.example.presentation.ui.stat.viewmodel.StatViewModel
import com.example.util.NetworkErrorMapper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

  private val apiModule = module {
    single { provideHttpClient(applicationContext) }
    single { provideRetrofit(get()) }
    single { provideAuctionService(get()) }
    single { provideLeaderboardService(get()) }
    single { provideHomeIntroService(get()) }
  }

  private val repoModule = module {

    single { NetworkResponseHelper(get<NetworkErrorMapper>()) }
    factory { NetworkErrorMapper() }
    factory { DefaultAuctionRepository(get(), get()) }
    factory { AuctionRepository(get<DefaultAuctionRepository>()) }

    factory { DefaultPvPRepository(get(), get(), get()) }
    factory { PvPRepository(get<DefaultPvPRepository>()) }

    factory { DefaultStatRepository(get(), get()) }
    factory { StatRepository(get<DefaultStatRepository>()) }

    factory { DefaultLeaderboardsRepository(get(), get()) }
    factory { LeaderboardRepository(get<DefaultLeaderboardsRepository>()) }

    factory { DefaultPvERepository(get(), get(), get()) }
    factory { PvERepository(get<DefaultPvERepository>()) }

    factory {DefaultHomeImpl(get(), get()) }
  }

  private val mappersModule = module {
    factory { PvPPlayerMapper() }
    factory { PvEPlayerMapper() }
  }

  private val useCaseModule = module {
    factory { GetListOfAuctions(get()) }
    factory { GetNumberOfSearchResults(get()) }
    factory { GetStatValues(get(), get()) }
    factory { GetRanges(get()) }
    factory { GetMaps(get()) }
    factory { GetPvPPlayers(get()) }
    factory { GetNumOfPvPSearchResult(get()) }
    factory { GetPvEPlayers(get()) }
    factory { GetNumOfPvESearchResult(get()) }
    factory { GetHomeIntroText(get()) }
  }

  private val viewModelModule = module {
    viewModel { AuctionViewModel(get()) }
    viewModel { StatViewModel(get()) }
    viewModel { PvPPlayerViewModel(get(), get()) }
    viewModel { LeaderboardsViewModel(get(), get()) }
    viewModel { PvEPlayerViewModel(get(), get()) }
    viewModel { HomeViewModel(get()) }
  }

  private val adapterModule = module {
    factory { AuctionAdapter() }
    factory { StatAdapter() }
    factory { PvPAdapter() }
    factory { PvEAdapter() }
  }

  private val dictionaryModule = module {
    factory { Dictionary(applicationContext) }
  }

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      androidContext(this@App)
      modules(
        listOf(
          apiModule,
          repoModule,
          mappersModule,
          useCaseModule,
          viewModelModule,
          dictionaryModule,
          adapterModule
        )
      )
    }
  }
}
