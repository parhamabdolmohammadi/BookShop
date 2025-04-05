package com.bcit.lecture10bby.data.com.bcit.termproject2.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.gson.gson

const val API_KEY = "BJgjwi4iD4tGM3b2RvuYNjpgxf7YZ6zlA333QZNa6XaUIlkm70QR4v8n7b70"

//entry point for creating HTTP request
public val client = HttpClient {
    install(ContentNegotiation) {
        gson()
    }

    //add additional headers to our requests
    defaultRequest {
//        header(HttpHeaders.Authorization, "Bearer $API_KEY")
    }
}