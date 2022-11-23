package com.example.domain.di

import com.example.domain.CoinRepository
import com.example.domain.usecase.GetCoinsListImpl
import com.example.domain.usecase.GetCoinsListUseCase
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
class DomainModule {

    @Provides
    @Singleton
    fun provideGetCoinsList(coinRepository: CoinRepository) : GetCoinsListUseCase = GetCoinsListImpl(coinRepository)
}