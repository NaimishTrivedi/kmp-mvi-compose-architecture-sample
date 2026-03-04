package com.om.ecommercedemo.di

import com.om.ecommercedemo.network.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import kotlin.coroutines.EmptyCoroutineContext.get


val networkModule  = module {

    single {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            prettyPrint = true
        }
    }

    single {
        createHttpClient(get())
    }

    /*single {
        HttpClient {
            install(DefaultRequest){
                url("https://dummyjson.com/") // Your Base URL here
                header("Content-Type", "application/json")
            }

            install(ContentNegotiation) {
                json(get())
            }

            expectSuccess = false
        }
    }*/
}