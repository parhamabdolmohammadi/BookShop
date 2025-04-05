package com.bcit.lecture10bby.data.com.bcit.termproject2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.BookRepository
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.BooksResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch


class BookState(private val repository: BookRepository): ViewModel() {

    var bookResponse by mutableStateOf<BooksResponse?>(null)
    var searchFlow = MutableStateFlow("")

    suspend fun getBooks() {
        bookResponse = repository.getBooks()
    }

    suspend fun getBook(str: String) {
        bookResponse = repository.getBook(str)
    }

    init {
        collectSearchInput()
    }

    //    @OptIn(FlowPreview::class)
    private fun collectSearchInput() {
        viewModelScope.launch {
            searchFlow
                .debounce(1000L)
                .collect {
                    bookResponse = repository.search(it)
                }
        }
    }

//    fun search(str: String) {
////        artwork = repository.search(str)
//        viewModelScope.launch {
//            artwork = repository.search(str)
//        }
//    }
}