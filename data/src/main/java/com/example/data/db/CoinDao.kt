package com.example.data.db

import androidx.room.*
import com.example.data.model.local.CoinInfoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@Dao
interface CoinDao {

    @Query("SELECT * FROM coin_info ORDER BY cmc_rank ASC")
    fun getAll(): Flow<List<CoinInfoEntity>>

    @Query("SELECT * FROM coin_info ORDER BY cmc_rank ASC LIMIT :limit OFFSET :offset ")
    fun getByPage(limit: Int, offset: Int): Flow<List<CoinInfoEntity>>

    @Insert
    suspend fun insert(coinInfoEntity: CoinInfoEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(coins: List<CoinInfoEntity>)

    @Query("DELETE FROM coin_info")
    suspend fun deleteAll()

    @Query("SELECT COUNT(coin_id) FROM coin_info")
    fun getRowCount() : Flow<Int>
}