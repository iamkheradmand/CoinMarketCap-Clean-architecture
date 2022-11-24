package com.example.domain.entities

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

data class FilterModel(
    val percent_change_24_min : Long ?= null,
    val percent_change_24_max : Long ?= null,
    val volume_24_min : Long ?= null,
    val volume_24_max : Long ?= null,
)
