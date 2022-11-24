package com.example.data.source

import com.example.data.datasource.CoinLocalSource
import com.example.data.db.CoinDao
import com.example.data.model.local.CoinInfoEntity
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

class CoinLocalSourceImpl @Inject constructor(private val coinDao: CoinDao) : CoinLocalSource {

    override suspend fun getCoinsList() = coinDao.getAll()

    override suspend fun insertAllCoins(coins: List<CoinInfoEntity>) = coinDao.insertAll(coins)
}