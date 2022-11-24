package com.example.data.mapper

import com.example.data.model.remote.GetInfoResponse
import com.example.data.model.remote.QueryModel
import com.example.domain.entities.FilterModel
import com.example.domain.entities.InfoDomainModel
import com.example.domain.entities.SortModel
import retrofit2.http.Query
import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

interface SortFilterToQueryMapper {
    fun toQueryModel(pageN: Int, sortModel: SortModel?, filterModel: FilterModel?): QueryModel
}

class QueryMapperImpl @Inject constructor() : SortFilterToQueryMapper {

    override fun toQueryModel(
        pageN: Int,
        sortModel: SortModel?,
        filterModel: FilterModel?
    ): QueryModel {
        return QueryModel(
            page = pageN,
            sort = sortModel?.sort,
            sort_dir = sortModel?.sort_dir,
            percent_change_24_min = filterModel?.percent_change_24_min,
            percent_change_24_max = filterModel?.percent_change_24_max,
            volume_24_min = filterModel?.volume_24_min,
            volume_24_max = filterModel?.volume_24_max,
        )
    }
}

