package com.example.data.datasource

import com.example.data.model.remote.GetCoinBaseResponse
import okhttp3.ResponseBody

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinRemoteSource {

    suspend fun getCoinsList() : GetCoinBaseResponse

    suspend fun getInfo(id : Long) : ResponseBody

}