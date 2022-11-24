package com.example.domain

import com.example.domain.entities.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinRepository {

    suspend fun getCoinsList(): Flow<ApiResult<List<CoinDomainModel>>>

    suspend fun getCoinsListLocal(): Flow<List<CoinDomainModel>>

    suspend fun getInfo(id: Long): Flow<ApiResult<InfoDomainModel>>

    suspend fun getCoinsListByQuery(page : Int, sortModel: SortModel, filterModel: FilterModel): Flow<ApiResult<List<CoinDomainModel>>>
}