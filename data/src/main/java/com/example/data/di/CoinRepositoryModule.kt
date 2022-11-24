package com.example.data.di

import com.example.data.ApiService
import com.example.data.datasource.CoinLocalSource
import com.example.data.datasource.CoinRemoteSource
import com.example.data.db.CoinDao
import com.example.data.mapper.GetCoinBaseResponseMapperImpl
import com.example.data.mapper.GetCoinBaseResponseToDomainModelMapper
import com.example.data.mapper.GetInfoResponseMapperImpl
import com.example.data.mapper.GetInfoResponseToDomainModelMapper
import com.example.data.repository.CoinRepositoryImpl
import com.example.data.source.CoinLocalSourceImpl
import com.example.data.source.CoinRemoteSourceImpl
import com.example.domain.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

@Module
@InstallIn(SingletonComponent::class)
object CoinRepositoryModule {

    @Provides
    @Singleton
    fun provideCoinRemoteSource(apiService: ApiService): CoinRemoteSource =
        CoinRemoteSourceImpl(apiService)

    @Provides
    @Singleton
    fun provideCoinLocalSource(coinDao: CoinDao): CoinLocalSource = CoinLocalSourceImpl(coinDao)

    @Provides
    @Singleton
    fun provideGetCoinBaseResponseToDomainModelMapper(): GetCoinBaseResponseToDomainModelMapper =
        GetCoinBaseResponseMapperImpl()

    @Provides
    @Singleton
    fun provideGetInfoResponseToDomainModelMapper(): GetInfoResponseToDomainModelMapper =
        GetInfoResponseMapperImpl()

    @Provides
    @Singleton
    fun provideCoinRepository(
        coinLocalSource: CoinLocalSource,
        remoteSource: CoinRemoteSource,
        coinBaseResponseMapper: GetCoinBaseResponseToDomainModelMapper,
        infoResponseToDomainModelMapper: GetInfoResponseToDomainModelMapper,
    ): CoinRepository = CoinRepositoryImpl(
        coinLocalSource,
        remoteSource,
        coinBaseResponseMapper,
        infoResponseToDomainModelMapper
    )
}