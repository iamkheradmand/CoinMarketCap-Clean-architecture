package com.example.domain.usecase

import com.example.domain.CoinRepository
import com.example.domain.entities.ApiResult
import com.example.domain.entities.CoinDomainModel
import com.example.domain.entities.FilterModel
import com.example.domain.entities.SortModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface GetCoinsListUseCase {
    suspend fun getCoinsList(): Flow<ApiResult<List<CoinDomainModel>>>
    suspend fun getCoinsListLocal(): Flow<List<CoinDomainModel>>
    suspend fun getCoinsListByQuery(page : Int, sortModel: SortModel, filterModel: FilterModel): Flow<ApiResult<List<CoinDomainModel>>>
}

class GetCoinsListImpl @Inject constructor(private val coinRepository: CoinRepository) :
    GetCoinsListUseCase {

    override suspend fun getCoinsList(): Flow<ApiResult<List<CoinDomainModel>>> =
        coinRepository.getCoinsList()

    override suspend fun getCoinsListLocal(): Flow<List<CoinDomainModel>> =
        coinRepository.getCoinsListLocal()

    override suspend fun getCoinsListByQuery(
        page: Int,
        sortModel: SortModel,
        filterModel: FilterModel
    ): Flow<ApiResult<List<CoinDomainModel>>> =
        coinRepository.getCoinsListByQuery(page, sortModel, filterModel)


}