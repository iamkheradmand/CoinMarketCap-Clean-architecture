package com.example.data.mapper

import com.example.data.model.remote.GetCoinResponse
import com.example.data.model.remote.GetInfoResponse
import com.example.domain.entities.CoinDomainModel
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface GetCoinBaseResponseToDomainModelMapper {
    fun toDomainModel(coinResponse: GetCoinResponse, getInfoResponse: GetInfoResponse?): CoinDomainModel
}

class GetCoinBaseResponseMapperImpl @Inject constructor() : GetCoinBaseResponseToDomainModelMapper {
    override fun toDomainModel(coinResponse: GetCoinResponse,
                               getInfoResponse: GetInfoResponse?): CoinDomainModel {
        return CoinDomainModel(
            id = coinResponse.id,
            logo = getInfoResponse?.logo ?: "error",
            name = coinResponse.name,
            symbol = coinResponse.symbol,
            priceByUsd = coinResponse.quote.price,
            percent_change_24hByUSD = coinResponse.quote.percentChange24h,
            market_cap = coinResponse.quote.marketCap
        )
    }
}

