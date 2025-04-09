package com.bcit.termproject2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Danton Soares - A01351419
 * Parham Abdolmohammadi - A01356970
 */

@Composable
fun CopyListDialog(onDismiss: () -> Unit, onCopy: (String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Copy Book List") },
        text = {
            Column {
                listOf("Read", "Reading", "WantToRead", "All").forEach { listType ->
                    val label = when (listType) {
                        "WantToRead" -> "Want To Read"
                        "All" -> "All Lists"
                        else -> listType
                    }
                    Button(
                        onClick = { onCopy(listType) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text("Copy $label")
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun copyBooksToClipboard(context: Context, listType: String) {
    val db = Database.getInstance(context)
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val scope = CoroutineScope(Dispatchers.IO)

    scope.launch {
        val bookDao = db.bookDao()

        val books = when (listType) {
            "All" -> listOf("Read", "Reading", "WantToRead").flatMap {
                bookDao.getBooksByListType(it).map { book -> it to book }
            }
            else -> bookDao.getBooksByListType(listType).map { listType to it }
        }

        val grouped = books.groupBy({ it.first }, { it.second })
        val builder = StringBuilder()

        grouped.forEach { (list, booksInList) ->
            val label = when (list) {
                "WantToRead" -> "Want To Read"
                else -> list
            }
            builder.appendLine("📚 $label:")
            booksInList.forEach { book ->
                builder.appendLine("${book.title} - ${book.author} - ${book.year ?: "Unknown"}")
            }
            builder.appendLine()
        }

        val clip = ClipData.newPlainText("Saved Books", builder.toString().trim())
        clipboard.setPrimaryClip(clip)

        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
        }
    }
}
