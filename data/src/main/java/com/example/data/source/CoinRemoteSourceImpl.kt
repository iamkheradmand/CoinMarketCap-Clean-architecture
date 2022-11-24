package com.example.data.source

import com.example.data.ApiService
import com.example.data.datasource.CoinRemoteSource
import com.example.data.model.remote.GetCoinBaseResponse
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

class CoinRemoteSourceImpl @Inject constructor(private val apiService: ApiService) : CoinRemoteSource {

    override suspend fun getCoinsList(): GetCoinBaseResponse {
        return apiService.getCoinsList(10)
    }

    override suspend fun getInfo(id: Long): ResponseBody {
        return apiService.getInfo(id)
    }

}