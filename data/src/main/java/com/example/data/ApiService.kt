package com.example.data

import com.example.data.model.GetCoinBaseResponse
import com.example.data.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface ApiService {

    @GET(Constants.URL_LISTINGS_LATEST)
    @Headers("Accept: application/json", "X-CMC_PRO_API_KEY: ${Constants.API_KEY}")
    suspend fun getCoinsList(): GetCoinBaseResponse
}