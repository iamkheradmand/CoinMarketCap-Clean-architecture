package com.example.data.mapper

import com.example.data.model.remote.GetInfoResponse
import com.example.domain.entities.InfoDomainModel
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

interface GetInfoResponseToDomainModelMapper {
    fun toDomainModel(infoResponse: GetInfoResponse): InfoDomainModel
}

class GetInfoResponseMapperImpl @Inject constructor() : GetInfoResponseToDomainModelMapper {

    override fun toDomainModel(infoResponse: GetInfoResponse): InfoDomainModel {
        return InfoDomainModel(
            id = infoResponse.id,
            logo = infoResponse.logo,
            name = infoResponse.name,
            symbol = infoResponse.symbol,
            description = infoResponse.description,
            website = infoResponse.urls.website,
        )
    }
}

