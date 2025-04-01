package com.bcit.lecture10bby.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson

// Entry point for creating HTTP requests
val client = HttpClient{
    //install the gson serializer into the ktor client
    install(ContentNegotiation){
        gson()
    }

    //add additional headers to our requests
    defaultRequest {
//        header(HttpHeaders.Authorization, "Bearer $API_KEY")
    }
}