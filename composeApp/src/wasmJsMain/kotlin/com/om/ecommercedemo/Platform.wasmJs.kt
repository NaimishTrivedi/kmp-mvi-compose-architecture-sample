package com.om.ecommercedemo

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.js.Js
import kotlinx.serialization.json.Json

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual fun platformHttpClient(
    json: Json,
    block: HttpClientConfig<*>.() -> Unit
): HttpClient {

    return HttpClient(Js) {
        block()
    }
}