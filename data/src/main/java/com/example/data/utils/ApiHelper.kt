package com.example.data.utils

import javax.inject.Inject

/**
 * Created by Amir mohammad Kheradmand on 11/25/2022.
 */

interface RepositoryHelper {
    fun calculateOffsetRemote(page : Int) : Int
}

class RepositoryHelperImpl @Inject constructor() : RepositoryHelper {

    override fun calculateOffsetRemote(page: Int): Int {
        val to = page * Constants.PAGE_SIZE
        val fromOffset = to - (Constants.PAGE_SIZE-1)
        return fromOffset
    }

}

