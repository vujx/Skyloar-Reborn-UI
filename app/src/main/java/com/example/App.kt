package com.example

import android.app.Application
import android.content.res.Resources
import com.example.data.di.ApiServiceAuctionModule.provideAuctionService
import com.example.data.di.ApiServiceAuctionModule.provideHttpClient
import com.example.data.di.ApiServiceAuctionModule.provideRetrofit
import com.example.data.di.UseCaseModule.provideAuctionUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    private val apiModule = module {
        single { provideHttpClient() }
        single { provideRetrofit(get()) }
        single { provideAuctionService(get()) }
    }

    private val useCaseModule = module {
        factory { provideAuctionUseCase(get()) }
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
                    useCaseModule
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
