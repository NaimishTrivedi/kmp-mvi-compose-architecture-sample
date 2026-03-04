package com.om.ecommercedemo.network

import com.om.ecommercedemo.platformHttpClient
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

fun createHttpClient(json: Json): HttpClient {
    return platformHttpClient(json) {
        commonConfig(json)
    }
}
