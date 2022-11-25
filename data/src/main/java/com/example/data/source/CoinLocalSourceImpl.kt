package com.example.data.source

import com.example.data.datasource.CoinLocalSource
import com.example.data.db.CoinDao
import com.example.data.model.local.CoinInfoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

class CoinLocalSourceImpl @Inject constructor(private val coinDao: CoinDao) : CoinLocalSource {

    override suspend fun getRowCount() = coinDao.getRowCount()

    override suspend fun getCoinsList() = coinDao.getAll()

    override suspend fun getByPage(limit: Int, offset: Int): Flow<List<CoinInfoEntity>> =
        coinDao.getByPage(limit, offset)

    override suspend fun insertAllCoins(coins: List<CoinInfoEntity>) = coinDao.insertAll(coins)

    override suspend fun deleteAll() = coinDao.deleteAll()
}