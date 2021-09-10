package com.example.data.di

import com.example.data.usecase.AuctionUseCase
import com.example.data.usecase.StatUseCase
import com.example.data.usecase.leaderboards.LeaderBoardsUseCase
import com.example.data.usecase.leaderboards.PvPUseCase
import com.example.domain.repository.auction.AuctionRepository
import com.example.domain.repository.leaderboard.LeaderboardRepository
import com.example.domain.repository.leaderboard.pvp.PvPRepository
import com.example.domain.repository.stat.StatRepository
import com.example.domain.usecase.auction.*
import com.example.domain.usecase.leaderboards.GetRanges
import com.example.domain.usecase.leaderboards.GetTimeToRefresh
import com.example.domain.usecase.leaderboards.pvp.GetNumOfPvPSearchResult
import com.example.domain.usecase.leaderboards.pvp.GetPvPPlayers
import com.example.domain.usecase.stat.GetStatValues

object UseCaseModule {

    fun provideAuctionUseCase(auctionRepo: AuctionRepository) =
        AuctionUseCase(
            GetListOfAuctions(auctionRepo),
            GetNumberOfSearchResults(auctionRepo)
        )

    fun provideStatUseCase(statRepo: StatRepository) =
        StatUseCase(
            GetStatValues(statRepo)
        )

    fun providePvP1UseCase(pvpRepo: PvPRepository) =
        PvPUseCase(
            GetPvPPlayers(pvpRepo),
            GetNumOfPvPSearchResult(pvpRepo)
        )

    fun provideLeaderBoardsUseCase(leaderboardRepo: LeaderboardRepository) =
        LeaderBoardsUseCase(
            GetRanges(leaderboardRepo),
            GetTimeToRefresh(leaderboardRepo)
        )
}
