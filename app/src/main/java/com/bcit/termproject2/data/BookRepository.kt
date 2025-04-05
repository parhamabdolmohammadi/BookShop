package com.bcit.lecture10bby.data.com.bcit.termproject2.data

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


class BookRepository(private val httpClient: HttpClient) {



    suspend fun getBooks() : BooksResponse? {
        val randomNumber = (0..100).random()

        val response = httpClient.get(BASE_URL.format(randomNumber.toString()))

        val json = response.body<JsonObject>().toString()

        return Gson().fromJson(json, BooksResponse::class.java)
    }

    suspend fun getBook(str : String) : BooksResponse? {
        val randomNumber = (0..100).random()

        val response = httpClient.get(BASE_URL.format(str))

        val json = response.body<JsonObject>().toString()

        return Gson().fromJson(json, BooksResponse::class.java)
    }




    suspend fun search(str: String) : BooksResponse? {

        val response = httpClient.get(BASE_URL.format(str))

        val json = response.body<JsonObject>().toString()

        return Gson().fromJson(json, BooksResponse::class.java)
    }
}