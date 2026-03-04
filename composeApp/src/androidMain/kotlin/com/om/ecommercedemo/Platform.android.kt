package com.om.ecommercedemo

import android.os.Build
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.serialization.json.Json

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun platformHttpClient(
    json: Json,
    block: HttpClientConfig<*>.() -> Unit
): HttpClient {

    return HttpClient(OkHttp) {
        block()
    }
}
