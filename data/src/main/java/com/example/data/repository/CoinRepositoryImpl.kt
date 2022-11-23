package com.example.data.repository

import android.util.Log
import com.example.data.datasource.CoinRemoteSource
import com.example.data.mapper.GetCoinBaseResponseMapperImpl
import com.example.data.mapper.GetCoinBaseResponseToDomainModelMapper
import com.example.domain.CoinRepository
import com.example.domain.entities.ApiResult
import com.example.domain.entities.CoinDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

class CoinRepositoryImpl @Inject constructor(
    private val remoteSource: CoinRemoteSource,
    private val coinBaseResponseMapper: GetCoinBaseResponseToDomainModelMapper
) : CoinRepository {

    override suspend fun getCoinsList(): Flow<ApiResult<List<CoinDomainModel>>> =
        flow {
            try {
                Log.e("CoinRepositoryImpl", "getCoinsList")
                val result = remoteSource.getCoinsList().data.map {
                    Log.e("CoinRepositoryImpl", "map = " + it.name)
                    coinBaseResponseMapper.toDomainModel(it)
                }
//                val map = coinBaseResponseMapper.toDomainModel(result)
                emit(ApiResult.Success(result))
            } catch (e: Exception) {
                Log.e("CoinRepositoryImpl", e.toString())
                emit(ApiResult.Failure(e.toString()))
            }
        }.flowOn(Dispatchers.IO)


}