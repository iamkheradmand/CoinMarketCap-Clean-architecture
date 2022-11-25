package com.example.data.mapper

import com.example.data.model.remote.QueryModel
import com.example.domain.entities.FilterModel
import com.example.domain.entities.SortModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Created by Amir mohammad Kheradmand on 11/25/2022.
 */

@RunWith(Parameterized::class)
class QueryMapperTest(
    private val start: Int,
    private val sortModel: SortModel?,
    private val filterModel: FilterModel?,
    private val expected: QueryModel
) {

    private lateinit var queryMapperImpl: QueryMapperImpl

    @Before
    fun setUp() {
        queryMapperImpl = QueryMapperImpl()
    }


    @Test
    fun testQueryMapperImpl() {
        val actualValue = queryMapperImpl.toQueryModel(start, sortModel, filterModel)
        assertEquals(expected, actualValue)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any?>> {
            return listOf(
                arrayOf(
                    1,
                    SortModel("name", "asc"),
                    FilterModel(-100, 500, 1000, 20000),
                    QueryModel(
                        1,
                        "name",
                        "asc",
                        -100,
                        500,
                        1000,
                        20000
                    )
                ),
                arrayOf(
                    21,
                    null,
                    FilterModel(2500, 9000),
                    QueryModel(
                        21,
                        null,
                        null,
                        2500,
                        9000,
                        null,
                        null
                    )
                ),
                arrayOf(
                    41,
                    SortModel("name", "desc"),
                    null,
                    QueryModel(
                        41,
                        "name",
                        "desc",
                        null,
                        null,
                        null,
                        null
                    )
                )
            )
        }
    }

}