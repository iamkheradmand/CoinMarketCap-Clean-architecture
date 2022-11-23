package com.example.data.datasource

import com.example.data.model.GetCoinBaseResponse

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinRemoteSource {

    suspend fun getCoinsList() : GetCoinBaseResponse

}