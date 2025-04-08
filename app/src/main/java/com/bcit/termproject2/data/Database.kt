package com.bcit.lecture10bby.data.com.bcit.termproject2.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Transaction


@Entity(tableName = "saved_books")
data class SavedBook(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val year: String?,
    val description: String?,
    val imageUrl: String?,
    val listType: String, // "READ", "READING", "WANT_TO_READ"
    val genre: String?,
)

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: SavedBook)

    @Query("SELECT * FROM saved_books WHERE listType = :type")
    suspend fun getBooksByListType(type: String): List<SavedBook>

    @Delete
    suspend fun deleteBook(book: SavedBook)

    @Query("SELECT * FROM saved_books WHERE id = :id")
    suspend fun getBookById(id: String): SavedBook?

    @Query("SELECT * FROM saved_books WHERE title = :title")
    suspend fun getBookByTitle(title: String): SavedBook?

    @Query("SELECT * FROM saved_books WHERE author = :author")
    suspend fun getBooksByAuthor(author: String): List<SavedBook>

    @Query("SELECT * FROM saved_books WHERE year = :year")
    suspend fun getBooksByYear(year: String): List<SavedBook>

    @Query("SELECT * FROM saved_books WHERE genre = :genre")
    suspend fun getBooksByGenre(genre: String): List<SavedBook>

    @Transaction
    @Query("SELECT * FROM saved_books WHERE listType = :listType")
    suspend fun getBooksInListType(listType: String): List<SavedBook>

}

@Database(entities = [SavedBook::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

object Database {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "books_db"
            ).build()
        }
        return INSTANCE!!
    }
}
