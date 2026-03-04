package com.om.ecommercedemo

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js
import kotlin.js.Json

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()

actual fun platformHttpClient(
    json: kotlinx.serialization.json.Json,
    block: HttpClientConfig<*>.() -> Unit
): HttpClient {

    return HttpClient(Js) {
        block()
    }
}
