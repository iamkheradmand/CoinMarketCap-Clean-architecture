package com.example.domain

import com.example.domain.entities.ApiResult
import com.example.domain.entities.CoinDomainModel
import com.example.domain.entities.InfoDomainModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinRepository {

    suspend fun getCoinsList() : Flow<ApiResult<List<CoinDomainModel>>>

    suspend fun getCoinsListLocal() : Flow<List<CoinDomainModel>>

    suspend fun getInfo(id : Long) : Flow<ApiResult<InfoDomainModel>>
}