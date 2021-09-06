package com.example

import android.app.Application
import android.content.res.Resources
import com.example.data.di.ApiServiceAuctionModule.provideAuctionService
import com.example.data.di.ApiServiceAuctionModule.provideHttpClient
import com.example.data.di.ApiServiceAuctionModule.provideRetrofit
import com.example.data.di.UseCaseModule.provideAuctionUseCase
import com.example.data.di.UseCaseModule.provideStatUseCase
import com.example.data.repository.DefaultAuctionRepository
import com.example.data.repository.DefaultStatRepository
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.repository.stat.StatRepository
import com.example.presentation.ui.auctions.adapter.AuctionAdapter
import com.example.presentation.ui.auctions.viewmodel.AuctionViewModel
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
    }

    private val repoModule = module {
        single { DefaultAuctionRepository(get()) }
        single { AuctionRepository(get<DefaultAuctionRepository>()) }

        single { DefaultStatRepository(get()) }
        single { StatRepository(get<DefaultStatRepository>()) }
    }

    private val useCaseModule = module {
        factory { provideAuctionUseCase(get()) }
        factory { provideStatUseCase(get()) }
    }

    private val viewModelModule = module {
        viewModel {
            AuctionViewModel(get())
        }

        viewModel {
            StatViewModel(get())
        }
    }

    private val adapterModule = module {
        factory { AuctionAdapter() }
        factory { StatAdapter() }
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
