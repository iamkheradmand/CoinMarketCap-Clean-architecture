package com.example.data.model.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

data class QuoteModel(
    @SerializedName("USD")
    @Expose
    val usd: USDModel
)
data class USDModel(
    @SerializedName("price")
    @Expose
    val price: Double,
    @SerializedName("volume_change_24h")
    @Expose
    val volumeChange24h: Double,
    @SerializedName("percent_change_24h")
    @Expose
    val percentChange24h: Double,
    @SerializedName("market_cap")
    @Expose
    val marketCap: Double ?= null,
)