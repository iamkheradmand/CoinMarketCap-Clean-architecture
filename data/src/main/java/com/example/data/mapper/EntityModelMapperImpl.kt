package com.example.data.mapper

import com.example.data.model.local.CoinInfoEntity
import com.example.domain.entities.CoinDomainModel
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/23/2022.
 */

interface CoinInfoEntityEntityMapper {
    fun toEntityModel(coinResponse: CoinDomainModel): CoinInfoEntity
    fun toCoinDomainModel(coinInfoEntity: CoinInfoEntity): CoinDomainModel
}

class CoinInfoEntityMapperImpl @Inject constructor() : CoinInfoEntityEntityMapper {
    override fun toEntityModel(coinResponse: CoinDomainModel): CoinInfoEntity {
       return CoinInfoEntity(
            coinId = coinResponse.id,
            coinLogo = coinResponse.logo,
            coinName = coinResponse.name,
            coinSymbol = coinResponse.symbol,
            coinPriceByUsd = coinResponse.priceByUsd,
            coinPercent_change_24hByUSD = coinResponse.percent_change_24hByUSD,
            coinMarketCap = coinResponse.market_cap ?: 0.0,
            cmc_rank = coinResponse.cmc_rank
        )
    }

    override fun toCoinDomainModel(coinInfoEntity: CoinInfoEntity): CoinDomainModel {
        return  CoinDomainModel(
            id = coinInfoEntity.coinId,
            logo = coinInfoEntity.coinLogo,
            name = coinInfoEntity.coinName,
            symbol = coinInfoEntity.coinSymbol,
            priceByUsd = coinInfoEntity.coinPriceByUsd,
            percent_change_24hByUSD = coinInfoEntity.coinPercent_change_24hByUSD,
            market_cap = coinInfoEntity.coinMarketCap,
            cmc_rank = coinInfoEntity.cmc_rank
        )
    }
}

