package com.example.domain.entities

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

data class CoinDomainModel(
    val id : Long,
    val logo : String,
    val name : String,
    val symbol : String,
    val priceByUsd : Double,
    val percent_change_24hByUSD : Double,
    val market_cap : String ?= null
)
