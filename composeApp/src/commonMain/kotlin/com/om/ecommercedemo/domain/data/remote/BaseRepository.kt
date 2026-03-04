package com.om.ecommercedemo.domain.data.remote

import com.om.ecommercedemo.domain.model.CategoryDto
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray.Companion.serializer


abstract class BaseRepository {

    suspend inline fun <reified T> safeApiCall(
        crossinline call: suspend () -> HttpResponse
    ): APIResult<T> {
        return try {
            val response = call()

            if (response.status.isSuccess()) {
                val body: T = response.body()
                APIResult.Success(body)
            } else {
                handleErrorRes(response.status.value)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            APIResult.Exception
        }
    }

    fun handleErrorRes(code: Int): APIResult.Error {
        return when (code) {
            401 -> APIResult.Error(code, "Session expired. Please login again.")
            404 -> APIResult.Error(code, "Resource not found.")
            500 -> APIResult.Error(code, "Internal Server Error. Please try again later.")
            else -> APIResult.Error(code, "Something went wrong. Error code: $code")
        }
    }
}