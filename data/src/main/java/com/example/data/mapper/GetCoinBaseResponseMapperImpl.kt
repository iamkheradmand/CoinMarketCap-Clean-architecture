package com.example.data.mapper

import com.example.data.model.GetCoinBaseResponse
import com.example.data.model.GetCoinResponse
import com.example.domain.entities.CoinDomainModel
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface GetCoinBaseResponseToDomainModelMapper {
    fun toDomainModel(coinResponse: GetCoinResponse): CoinDomainModel
}

class GetCoinBaseResponseMapperImpl @Inject constructor() : GetCoinBaseResponseToDomainModelMapper {
    override fun toDomainModel(coinResponse: GetCoinResponse): CoinDomainModel {
        return CoinDomainModel(
            id = coinResponse.id,
            name = coinResponse.name,
            symbol = coinResponse.symbol,
            priceByUsd = coinResponse.quote.price,
            percent_change_24hByUSD = coinResponse.quote.percentChange24h,
            market_cap = coinResponse.quote.marketCap
        )
    }
}

