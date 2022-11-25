package com.example.coinmarketcap.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.db.AppDatabase
import com.example.data.db.CoinDao
import com.example.data.model.local.CoinInfoEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Amir mohammad Kheradmand on 11/25/2022.
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RoomTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    private lateinit var database: AppDatabase
    private lateinit var coinDao: CoinDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        coinDao = database.coinDao()
    }

    @Test
    fun testInsertCoin(): Unit = runBlocking {
        val getCoinsList = async {
            coinDao.getAll().take(1).toList()
        }

        val coinInfoEntity = CoinInfoEntity(
            coinId = 1,
            coinLogo = "logoUrl",
            coinName = "Bitcoin",
            coinSymbol = "BTC",
            coinMarketCap = 454545.2,
            coinPercent_change_24hByUSD = 5880.0,
            coinPriceByUsd = 5000.3,
            cmc_rank = 1
        )
        coinDao.insert(coinInfoEntity)
        assertThat(getCoinsList.await()).containsExactly(arrayListOf(coinInfoEntity))
    }

//    @Test
//    fun testDeleteCoin(): Unit = runBlocking {
//        val getCoinsList = async {
//            coinDao.getAll().toList()
//        }
//
//        val coinInfoEntity = CoinInfoEntity(
//            coinId = 1,
//            coinLogo = "logoUrl",
//            coinName = "Bitcoin",
//            coinSymbol = "BTC",
//            coinMarketCap = 454545.2,
//            coinPercent_change_24hByUSD = 5880.0,
//            coinPriceByUsd = 5000.3,
//            cmc_rank = 1
//        )
//        coinDao.insert(coinInfoEntity)
//
//        coinDao.delete()
//
//        assertThat(getCoinsList.await()).containsExactly(null)
//    }

    @After
    fun closeDatabase() {
        database.close()
    }
}