package com.example.data.repository

import android.util.Log
import com.example.data.datasource.CoinLocalSource
import com.example.data.datasource.CoinRemoteSource
import com.example.data.db.AppDatabase
import com.example.data.mapper.GetCoinBaseResponseToDomainModelMapper
import com.example.data.mapper.GetInfoResponseToDomainModelMapper
import com.example.data.model.local.CoinInfoEntity
import com.example.data.model.remote.GetInfoResponse
import com.example.data.utils.Utils
import com.example.domain.CoinRepository
import com.example.domain.entities.ApiResult
import com.example.domain.entities.CoinDomainModel
import com.example.domain.entities.InfoDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.json.JSONObject
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

class CoinRepositoryImpl @Inject constructor(
    private val coinLocalSource: CoinLocalSource,
    private val remoteSource: CoinRemoteSource,
    private val coinBaseResponseMapper: GetCoinBaseResponseToDomainModelMapper,
    private val infoResponseMapper: GetInfoResponseToDomainModelMapper,
) : CoinRepository {

    override suspend fun getCoinsList(): Flow<ApiResult<List<CoinDomainModel>>> =
        flow {
            Log.e("CoinRepositoryImpl", "getCoinsList called")
            try {
                val result = remoteSource.getCoinsList().data.map { coinResponseModel ->
//                    Log.e("CoinRepositoryImpl", "map = " + coinResponseModel.name)
                    val infoResult = remoteSource.getInfo(coinResponseModel.id)
                    val mainJSONObj = JSONObject(infoResult.string()).getJSONObject("data")
                    val dataObject = mainJSONObj.getJSONObject("${coinResponseModel.id}").toString()
                    val getInfoModel = Utils.jsonConverter(dataObject, GetInfoResponse::class.java)
                    coinBaseResponseMapper.toDomainModel(coinResponseModel, getInfoModel)
                }

                val coinsList = result.map {
                    CoinInfoEntity(
                        coinId = it.id,
                        coinLogo = it.logo,
                        coinName = it.name,
                        coinSymbol = it.symbol,
                        coinPriceByUsd = it.priceByUsd,
                        coinPercent_change_24hByUSD = it.percent_change_24hByUSD,
                        coinMarketCap = it.market_cap ?: " "
                    )
                }

                coinLocalSource.insertAllCoins(coinsList)

                Log.e("CoinRepositoryImpl", "coinLocalSource database updated")

                emit(ApiResult.Success(result))
            } catch (e: Exception) {
                Log.e("CoinRepositoryImpl", e.toString())
                emit(ApiResult.Failure(e.toString()))
            }

        }.flowOn(Dispatchers.IO)


    override suspend fun getCoinsListLocal(): Flow<List<CoinDomainModel>> {
        return coinLocalSource.getCoinsList()
            .map {
                it.map { coinInfoEntity ->
                    CoinDomainModel(
                        id = coinInfoEntity.coinId,
                        logo = coinInfoEntity.coinLogo,
                        name = coinInfoEntity.coinName,
                        symbol = coinInfoEntity.coinSymbol,
                        priceByUsd = coinInfoEntity.coinPriceByUsd,
                        percent_change_24hByUSD = coinInfoEntity.coinPercent_change_24hByUSD,
                        market_cap = coinInfoEntity.coinMarketCap
                    )
                }
            }.flowOn(Dispatchers.IO)

    }


    override suspend fun getInfo(id: Long): Flow<ApiResult<InfoDomainModel>> =
        flow {
            try {
                val infoResult = remoteSource.getInfo(id)
                val mainJSONObj = JSONObject(infoResult.string()).getJSONObject("data")
                val dataObject = mainJSONObj.getJSONObject("${id}").toString()
                val getInfoModel = Utils.jsonConverter(dataObject, GetInfoResponse::class.java)

                val result = infoResponseMapper.toDomainModel(getInfoModel)
                emit(ApiResult.Success(result))
            } catch (e: Exception) {
                Log.e("CoinRepositoryImpl", " getInfo = " + e.toString())
                emit(ApiResult.Failure(e.toString()))
            }

        }.flowOn(Dispatchers.IO)


}