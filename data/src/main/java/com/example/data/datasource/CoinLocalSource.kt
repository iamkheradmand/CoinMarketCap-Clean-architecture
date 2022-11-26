package com.example.data.datasource

import com.example.data.model.local.CoinInfoEntity
import com.example.data.model.remote.GetCoinBaseResponse
import kotlinx.coroutines.flow.Flow

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinLocalSource {

    suspend fun getRowCount() : Flow<Int>

    suspend fun getCoinsList() : Flow<List<CoinInfoEntity>>

    suspend fun getByPage(limit: Int, offset: Int): List<CoinInfoEntity>

    suspend fun insertAllCoins(coins: List<CoinInfoEntity>)

    suspend fun deleteAll()

}