package com.example.data.model.remote

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

data class QueryModel(
    val start: Int = 1,
    val sort: String? = null,
    val sort_dir: String? = null,
    val percent_change_24_min: Long? = null,
    val percent_change_24_max: Long? = null,
    val volume_24_min: Long? = null,
    val volume_24_max: Long? = null,
)
