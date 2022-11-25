package com.example.data.utils

import java.io.InputStreamReader

/**
 * Created by Amir Mohammad Kheradmand on 11/25/2022.
 */

class MockResponseFileReader(path: String) {
    val content: String
    init {
        val reader = InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}