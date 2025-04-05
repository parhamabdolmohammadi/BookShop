package com.bcit.lecture10bby.data.com.bcit.termproject2.data

import com.google.gson.annotations.SerializedName

data class BooksResponse(
    @SerializedName("items")
    val items: List<BookItem>
)

data class BookItem(
    val id: String,
    @SerializedName("volumeInfo")
    val info: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String> = emptyList(),
    val publishedDate: String? = null,
    val imageLinks: ImageLinks? = null,
    val description : String
)

data class ImageLinks(
    @SerializedName("thumbnail")
    val thumbnail: String?
)
