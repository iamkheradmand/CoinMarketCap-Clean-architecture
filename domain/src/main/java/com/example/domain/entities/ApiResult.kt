package com.example.domain.entities

/**
 * Created by Amir mohammad Kheradmand on 6/09/2022.
 */

sealed class ApiResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Failure<T>(message: String, data: T? = null) : ApiResult<T>(data, message)
}