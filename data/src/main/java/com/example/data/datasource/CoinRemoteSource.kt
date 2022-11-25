package com.example.data.datasource

import com.example.data.model.remote.GetCoinBaseResponse
import com.example.data.model.remote.QueryModel
import okhttp3.ResponseBody

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinRemoteSource {

    suspend fun getCoinsList(start: Int) : GetCoinBaseResponse

    suspend fun getCoinsByQuery(queryModel: QueryModel) : GetCoinBaseResponse

    suspend fun getInfo(id : Long) : ResponseBody

}