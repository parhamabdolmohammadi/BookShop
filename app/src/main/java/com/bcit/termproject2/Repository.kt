package com.bcit.termproject2

class Repository(private val dao: BookDao) {
    suspend fun addBook(book: SavedBook) = dao.insertBook(book)
    suspend fun getBooks(type: String) = dao.getBooksByListType(type)
    suspend fun removeBook(book: SavedBook) = dao.deleteBook(book)
    suspend fun getBook(id: String) = dao.getBookById(id)
    suspend fun getBookByTitle(title: String) = dao.getBookByTitle(title)
    suspend fun getBooksByAuthor(author: String) = dao.getBooksByAuthor(author)
    suspend fun getBooksByYear(year: String) = dao.getBooksByYear(year)
    suspend fun getBooksByGenre(genre: String) = dao.getBooksByGenre(genre)
}
