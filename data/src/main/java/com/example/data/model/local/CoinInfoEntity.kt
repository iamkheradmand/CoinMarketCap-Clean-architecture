package com.example.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

@Entity(tableName = "coin_info")
data class CoinInfoEntity(
    @PrimaryKey @ColumnInfo(name = "coin_id") val coinId: Long,
    @ColumnInfo(name = "coin_logo") val coinLogo: String,
    @ColumnInfo(name = "coin_name") val coinName: String,
    @ColumnInfo(name = "coin_symbol") val coinSymbol: String,
    @ColumnInfo(name = "coin_priceByUsd") val coinPriceByUsd: Double,
    @ColumnInfo(name = "coin_percent_change_24hByUSD") val coinPercent_change_24hByUSD: Double,
    @ColumnInfo(name = "coin_market_cap") val coinMarketCap: String,
)
