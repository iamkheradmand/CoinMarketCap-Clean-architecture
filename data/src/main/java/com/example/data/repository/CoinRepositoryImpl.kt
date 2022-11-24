package com.example.data.repository

import android.util.Log
import com.example.data.datasource.CoinLocalSource
import com.example.data.datasource.CoinRemoteSource
import com.example.data.db.AppDatabase
import com.example.data.mapper.GetCoinBaseResponseToDomainModelMapper
import com.example.data.mapper.GetInfoResponseToDomainModelMapper
import com.example.data.mapper.SortFilterToQueryMapper
import com.example.data.model.local.CoinInfoEntity
import com.example.data.model.remote.GetInfoResponse
import com.example.data.utils.Constants
import com.example.data.utils.Utils
import com.example.domain.CoinRepository
import com.example.domain.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
    private val sortFilterToQueryMapper: SortFilterToQueryMapper
) : CoinRepository {

    override suspend fun getCoinsList(page: Int): Flow<ApiResult<List<CoinDomainModel>>> =
        flow {
            Log.e("CoinRepositoryImpl", "getCoinsList called")
            try {
                val result = remoteSource.getCoinsList(page).data.map { coinResponseModel ->
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

    override suspend fun getFromRoomByPage(page: Int): Flow<List<CoinDomainModel>> {
        val limit = page * Constants.PAGE_SIZE
        val offset = limit - Constants.PAGE_SIZE
        Log.e("HomeFragment", "offset $offset  limit $limit")

        return coinLocalSource.getByPage(limit, offset)
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

        }.flowOn(Dispatchers.IO).catch {
            emit(ApiResult.Failure("error"))
        }

    override suspend fun getCoinsListByQuery(
        page: Int,
        sortModel: SortModel?,
        filterModel: FilterModel?
    ): Flow<ApiResult<List<CoinDomainModel>>> =
        flow {
            try {
                Log.e(
                    "getCoinsListByQuery",
                    "filterModel: " + filterModel!!.volume_24_min + " = " + filterModel!!.percent_change_24_max
                )
                val queryModel = sortFilterToQueryMapper.toQueryModel(page, sortModel, filterModel)
                Log.e("getCoinsListByQuery", "queryModel " + queryModel.volume_24_min)

                val data = remoteSource.getCoinsByQuery(queryModel).data
                if (data.isNotEmpty()) {
                    Log.e("getCoinsListByQuery", "isNotEmpty ")
                    val result =
                        remoteSource.getCoinsByQuery(queryModel).data.map { coinResponseModel ->

                            val infoResult = remoteSource.getInfo(coinResponseModel.id)
                            val mainJSONObj = JSONObject(infoResult.string()).getJSONObject("data")
                            val dataObject =
                                mainJSONObj.getJSONObject("${coinResponseModel.id}").toString()
                            val getInfoModel =
                                Utils.jsonConverter(dataObject, GetInfoResponse::class.java)
                            coinBaseResponseMapper.toDomainModel(coinResponseModel, getInfoModel)
                        }

                    emit(ApiResult.Success(result))
                } else
                    emit(ApiResult.Failure("empty"))

            } catch (e: Exception) {
                emit(ApiResult.Failure(e.toString()))
            }

        }.flowOn(Dispatchers.IO)


}