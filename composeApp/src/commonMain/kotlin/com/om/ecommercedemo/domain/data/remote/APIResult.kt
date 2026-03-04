package com.om.ecommercedemo.domain.data.remote

sealed class APIResult<out T> {
    data class Success<out T>(val data: T) : APIResult<T>()
    data class Error(val code: Int? = null, val message: String? = null) : APIResult<Nothing>()
    object Exception : APIResult<Nothing>()
}