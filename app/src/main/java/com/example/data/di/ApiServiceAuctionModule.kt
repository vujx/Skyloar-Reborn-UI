package com.example.data.di

import com.example.data.network.AuctionService
import com.example.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServiceAuctionModule {

    fun provideAuctionService(retrofit: Retrofit): AuctionService {
        return retrofit.create(AuctionService::class.java)
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_AUCTION)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }

    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}
