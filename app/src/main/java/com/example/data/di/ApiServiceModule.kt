package com.example.data.di

import android.content.Context
import com.example.data.network.AuctionStatService
import com.example.data.network.LeaderboardService
import com.example.util.Constants
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServiceModule {

    fun provideAuctionService(retrofit: Retrofit): AuctionStatService {
        return retrofit.create(AuctionStatService::class.java)
    }

    fun provideLeaderboardService(retrofit: Retrofit): LeaderboardService {
        return retrofit.create(LeaderboardService::class.java)
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()
    }

    fun provideHttpClient(context: Context): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(ChuckInterceptor(context))
            .build()
    }
}
