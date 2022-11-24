package com.example.data.db

import androidx.room.*
import com.example.data.model.local.CoinInfoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@Dao
interface CoinDao {

    @Query("SELECT * FROM coin_info")
    fun getAll(): Flow<List<CoinInfoEntity>>

    @Insert
    suspend fun insert(coinInfoEntity: CoinInfoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<CoinInfoEntity>)

    @Delete
    suspend fun delete(coinInfoEntity: CoinInfoEntity)
}