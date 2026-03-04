package com.om.ecommercedemo

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import kotlinx.serialization.json.Json
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun platformHttpClient(
    json: Json,
    block: HttpClientConfig<*>.() -> Unit
): HttpClient {

    return HttpClient(Darwin) {
        block()
    }
}
