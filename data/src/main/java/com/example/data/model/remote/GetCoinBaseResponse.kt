package com.example.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

data class GetCoinBaseResponse(
    @SerializedName("data")
    @Expose
    val data: List<GetCoinResponse> = ArrayList()
)

data class GetCoinResponse(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("symbol")
    @Expose
    val symbol: String,
    @SerializedName("quote")
    @Expose
    val quote: QuoteModel
)