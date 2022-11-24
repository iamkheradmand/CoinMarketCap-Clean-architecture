package com.example.data

import com.example.data.model.remote.GetCoinBaseResponse
import com.example.data.utils.Constants
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface ApiService {

    @GET(Constants.URL_LISTINGS_LATEST)
    @Headers("Accept: application/json", "X-CMC_PRO_API_KEY: ${Constants.API_KEY}")
    suspend fun getCoinsList(@Query("limit") limit : Int): GetCoinBaseResponse

    @GET(Constants.URL_INFO)
    @Headers("Accept: application/json", "X-CMC_PRO_API_KEY: ${Constants.API_KEY}")
    suspend fun getInfo(@Query("id") id : Long): ResponseBody


}