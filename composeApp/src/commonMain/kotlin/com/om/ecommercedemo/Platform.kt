package com.om.ecommercedemo

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import kotlinx.serialization.json.Json

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun platformHttpClient(
    json: Json,
    block: HttpClientConfig<*>.() -> Unit
): HttpClient