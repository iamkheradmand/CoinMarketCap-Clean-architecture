package com.example.domain

import com.example.domain.entities.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinRepository {

    suspend fun getDatabaseRowCount() :Flow<Int>

    suspend fun clearDatabase()

    suspend fun updateDatabaseFromServer(page: Int)

    suspend fun getCoinsListByFlow(page: Int): Flow<ApiResult<List<CoinDomainModel>>>

    suspend fun getCoinsListLocal(): Flow<List<CoinDomainModel>>

    suspend fun getFromRoomByPage(page: Int): Flow<List<CoinDomainModel>>

    suspend fun getInfo(id: Long): Flow<ApiResult<InfoDomainModel>>

    suspend fun getCoinsListByQuery(page : Int, sortModel: SortModel?, filterModel: FilterModel?): Flow<ApiResult<List<CoinDomainModel>>>
}