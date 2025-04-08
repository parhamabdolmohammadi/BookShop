package com.bcit.lecture10bby.data.com.bcit.termproject2.data

import androidx.lifecycle.viewModelScope
import com.bcit.termproject2.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


class BookRepository(private val httpClient: HttpClient, private val context: android.content.Context) {



    suspend fun getBooks() : BooksResponse? {

        val queries = context.resources.getStringArray(R.array.queries).toList()
        val randomNumber = (0..queries.size).random()

        val response = httpClient.get(BASE_URL.format(queries[randomNumber])+"&orderBy=relevance")

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