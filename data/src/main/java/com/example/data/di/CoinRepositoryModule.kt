package com.example.data.di

import com.example.data.ApiService
import com.example.data.datasource.CoinLocalSource
import com.example.data.datasource.CoinRemoteSource
import com.example.data.db.CoinDao
import com.example.data.mapper.*
import com.example.data.repository.CoinRepositoryImpl
import com.example.data.source.CoinLocalSourceImpl
import com.example.data.source.CoinRemoteSourceImpl
import com.example.data.utils.RepositoryHelper
import com.example.data.utils.RepositoryHelperImpl
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
    fun provideQueryMapper(): SortFilterToQueryMapper = QueryMapperImpl()

    @Provides
    @Singleton
    fun provideRepositoryHelper(): RepositoryHelper = RepositoryHelperImpl()

    @Provides
    @Singleton
    fun provideCoinRepository(
        coinLocalSource: CoinLocalSource,
        remoteSource: CoinRemoteSource,
        coinBaseResponseMapper: GetCoinBaseResponseToDomainModelMapper,
        infoResponseToDomainModelMapper: GetInfoResponseToDomainModelMapper,
        sortFilterToQueryMapper: SortFilterToQueryMapper,
        repositoryHelper: RepositoryHelper
    ): CoinRepository = CoinRepositoryImpl(
        coinLocalSource,
        remoteSource,
        coinBaseResponseMapper,
        infoResponseToDomainModelMapper,
        sortFilterToQueryMapper,
        repositoryHelper
    )
}