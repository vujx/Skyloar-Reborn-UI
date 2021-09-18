package com.example

import android.app.Application
import android.content.res.Resources
import com.example.data.di.ApiServiceModule.provideAuctionService
import com.example.data.di.ApiServiceModule.provideHttpClient
import com.example.data.di.ApiServiceModule.provideLeaderboardService
import com.example.data.di.ApiServiceModule.provideRetrofit
import com.example.data.di.UseCaseModule.provideAuctionUseCase
import com.example.data.di.UseCaseModule.provideLeaderBoardsUseCase
import com.example.data.di.UseCaseModule.providePvEUseCase
import com.example.data.di.UseCaseModule.providePvP1UseCase
import com.example.data.di.UseCaseModule.provideStatUseCase
import com.example.data.repository.auction.DefaultAuctionRepository
import com.example.data.repository.leaderboards.DefaultLeaderboardsRepository
import com.example.data.repository.leaderboards.DefaultPvERepository
import com.example.data.repository.leaderboards.DefaultPvPRepository
import com.example.data.repository.stat.DefaultStatRepository
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.repository.leaderboard.LeaderboardRepository
import com.example.domain.repository.leaderboard.pve.PvERepository
import com.example.domain.repository.leaderboard.pvp.PvPRepository
import com.example.domain.repository.stat.StatRepository
import com.example.presentation.ui.auctions.adapter.AuctionAdapter
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
import com.example.presentation.ui.leaderboards.adapter.pvp.PvPAdapter
import com.example.presentation.ui.leaderboards.viewmodel.LeaderboardsViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvEPlayerViewModel
import com.example.presentation.ui.leaderboards.viewmodel.PvPPlayerViewModel
import com.example.presentation.ui.stat.adapter.StatAdapter
import com.example.presentation.ui.stat.viewmodel.StatViewModel
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
  }

  private val repoModule = module {
    single { DefaultAuctionRepository(get()) }
    single { AuctionRepository(get<DefaultAuctionRepository>()) }

    single { DefaultPvPRepository(get()) }
    single { PvPRepository(get<DefaultPvPRepository>()) }

    single { DefaultStatRepository(get()) }
    single { StatRepository(get<DefaultStatRepository>()) }

    single { DefaultLeaderboardsRepository(get()) }
    single { LeaderboardRepository(get<DefaultLeaderboardsRepository>()) }

    single { DefaultPvERepository(get()) }
    single { PvERepository(get<DefaultPvERepository>()) }
  }

  private val useCaseModule = module {
    factory { provideAuctionUseCase(get()) }
    factory { provideStatUseCase(get()) }
    factory { providePvP1UseCase(get()) }
    factory { provideLeaderBoardsUseCase(get()) }
    factory { providePvEUseCase(get()) }
  }

  private val viewModelModule = module {
    viewModel {
      AuctionViewModel(get())
    }
    viewModel {
      StatViewModel(get())
    }
    viewModel {
      PvPPlayerViewModel(get())
    }
    viewModel {
      LeaderboardsViewModel(get())
    }
    viewModel {
      PvEPlayerViewModel(get())
    }
  }

  private val adapterModule = module {
    factory { AuctionAdapter() }
    factory { StatAdapter() }
    factory { PvPAdapter() }
  }

  override fun onCreate() {
    super.onCreate()
    getResources = resources

    startKoin {
      androidLogger()
      androidContext(this@App)
      modules(
        listOf(
          apiModule,
          repoModule,
          useCaseModule,
          viewModelModule,
          adapterModule
        )
      )
    }
  }

  companion object {
    lateinit var getResources: Resources
    private fun getResource() = getResources
    fun getStringResource(id: Int) = getResource().getString(id)
  }
}
