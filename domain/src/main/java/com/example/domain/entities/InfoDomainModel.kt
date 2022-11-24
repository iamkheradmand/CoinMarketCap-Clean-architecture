package com.example.domain.entities

/**
 * Created by Amir mohammad Kheradmand on 11/24/2022.
 */

data class InfoDomainModel(
    val id : Long,
    val logo : String,
    val name : String,
    val symbol : String,
    val description : String,
    val website: ArrayList<String>)
