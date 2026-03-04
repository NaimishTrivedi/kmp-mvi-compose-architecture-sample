package com.om.ecommercedemo.network

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun HttpClientConfig<*>.commonConfig(json: Json) {

    install(DefaultRequest) {
        url("https://dummyjson.com/")
        header("Content-Type", "application/json")
    }

    install(ContentNegotiation) {
        json(json)
    }

    expectSuccess = false
}
