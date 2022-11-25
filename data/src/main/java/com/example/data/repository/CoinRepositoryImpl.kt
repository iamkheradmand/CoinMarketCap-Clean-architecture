package com.example.data.repository

import android.util.Log
import com.example.data.datasource.CoinLocalSource
import com.example.data.datasource.CoinRemoteSource
import com.example.data.mapper.CoinInfoEntityEntityMapper
import com.example.data.mapper.GetCoinBaseResponseToDomainModelMapper
import com.example.data.mapper.GetInfoResponseToDomainModelMapper
import com.example.data.mapper.SortFilterToQueryMapper
import com.example.data.model.remote.GetCoinResponse
import com.example.data.model.remote.GetInfoResponse
import com.example.data.utils.Constants
import com.example.data.utils.RepositoryHelper
import com.example.data.utils.Utils
import com.example.domain.CoinRepository
import com.example.domain.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val sortFilterToQueryMapper: SortFilterToQueryMapper,
    private val coinInfoEntityEntityMapper: CoinInfoEntityEntityMapper,
    private val repositoryHelper: RepositoryHelper
) : CoinRepository {

    override suspend fun getDatabaseRowCount(): Flow<Int> =
        coinLocalSource.getRowCount()

    override suspend fun updateDatabaseFromServer(page: Int) {
        try {
            val start = repositoryHelper.calculateOffsetRemote(page)
            val result = remoteSource.getCoinsList(start).data.map { coinResponseModel ->
                getCoinInfoMapToDomainModel(coinResponseModel)
            }

            if (page == 1) coinLocalSource.deleteAll()
            delay(300)
            //update Database
            val coinsList = result.map { coinInfoEntityEntityMapper.toEntityModel(it) }
            coinLocalSource.insertAllCoins(coinsList)
        } catch (e: Exception) {
            Log.e("CoinRepositoryImpl", "updateDatabaseFromServer Exception $e")
        }
    }

    override suspend fun getCoinsListByFlow(page: Int): Flow<ApiResult<List<CoinDomainModel>>> =
        flow {
            try {
                val start = repositoryHelper.calculateOffsetRemote(page)
                val result = remoteSource.getCoinsList(start).data.map { coinResponseModel ->
                    getCoinInfoMapToDomainModel(coinResponseModel)
                }
                emit(ApiResult.Success(result))
            } catch (e: Exception) {
                emit(ApiResult.Failure(e.toString()))
            }

        }.flowOn(Dispatchers.IO)


    private suspend fun getCoinInfoMapToDomainModel(coinResponseModel: GetCoinResponse): CoinDomainModel {
        val infoResult = remoteSource.getInfo(coinResponseModel.id)
        val mainJSONObj = JSONObject(infoResult.string()).getJSONObject("data")
        val dataObject = mainJSONObj.getJSONObject("${coinResponseModel.id}").toString()
        val getInfoModel = Utils.jsonConverter(dataObject, GetInfoResponse::class.java)
        return coinBaseResponseMapper.toDomainModel(coinResponseModel, getInfoModel)
    }

    override suspend fun getCoinsListLocal(): Flow<List<CoinDomainModel>> {
        return coinLocalSource.getCoinsList()
            .map {
                it.map { coinInfoEntity ->
                    coinInfoEntityEntityMapper.toCoinDomainModel(coinInfoEntity)
                }
            }.flowOn(Dispatchers.IO)

    }

    override suspend fun getFromRoomByPage(page: Int): Flow<List<CoinDomainModel>> {
        val limit = page * Constants.PAGE_SIZE
        val offset = limit - Constants.PAGE_SIZE
        return coinLocalSource.getByPage(limit, offset)
            .map {
                it.map { coinInfoEntity ->
                    coinInfoEntityEntityMapper.toCoinDomainModel(
                        coinInfoEntity
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
                val start = repositoryHelper.calculateOffsetRemote(page)
                val queryModel = sortFilterToQueryMapper.toQueryModel(start, sortModel, filterModel)

                val data = remoteSource.getCoinsByQuery(queryModel).data
                if (data.isNotEmpty()) {
                    val result =
                        remoteSource.getCoinsByQuery(queryModel).data.map { coinResponseModel ->
                            getCoinInfoMapToDomainModel(coinResponseModel)
                        }

                    emit(ApiResult.Success(result))
                } else
                    emit(ApiResult.Failure("To many requests!"))

            } catch (e: Exception) {
                emit(ApiResult.Failure(e.toString()))
            }

        }.flowOn(Dispatchers.IO)


    override suspend fun clearDatabase() {
        coinLocalSource.deleteAll()
    }

}