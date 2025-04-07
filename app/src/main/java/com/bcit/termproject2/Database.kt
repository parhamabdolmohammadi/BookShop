package com.bcit.termproject2

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
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
    val listType: String // "READ", "READING", "WANT_TO_READ"
)

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: SavedBook)

    @Query("SELECT * FROM saved_books WHERE listType = :type")
    suspend fun getBooksByListType(type: String): List<SavedBook>

    @Delete
    suspend fun deleteBook(book: SavedBook)
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
