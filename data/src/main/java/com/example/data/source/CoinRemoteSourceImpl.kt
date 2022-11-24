package com.example.data.source

import com.example.data.ApiService
import com.example.data.datasource.CoinRemoteSource
import com.example.data.model.remote.GetCoinBaseResponse
import com.example.data.model.remote.QueryModel
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

class CoinRemoteSourceImpl @Inject constructor(private val apiService: ApiService) :
    CoinRemoteSource {

    override suspend fun getCoinsList(): GetCoinBaseResponse {
        return apiService.getCoinsList()
    }

    override suspend fun getCoinsByQuery(queryModel: QueryModel): GetCoinBaseResponse {
        return apiService.getCoinsList(page = queryModel.page, sort = queryModel.sort,
            sort_dir = queryModel.sort_dir,
            percent_change_24h_min = queryModel.percent_change_24_min,
            percent_change_24h_max = queryModel.percent_change_24_max,
            volume_24h_min = queryModel.volume_24_min,
            volume_24h_max = queryModel.volume_24_max,
        )
    }

    override suspend fun getInfo(id: Long): ResponseBody {
        return apiService.getInfo(id)
    }

}