package com.bcit.lecture10bby.data.com.bcit.termproject2

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Database
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.SavedBook
import kotlinx.coroutines.launch

@Composable
fun BookListScreen(
    listType: String,
    context: Context = LocalContext.current
) {
    val db = remember { Database.getInstance(context) }
    var books by remember { mutableStateOf<List<SavedBook>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listType) {
        coroutineScope.launch {
            books = db.bookDao().getBooksByListType(listType)
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        val (icon, label, color) = when (listType) {
            "Read" -> Triple<ImageVector, String, Color>(Icons.Default.Check, "Read", Color(0xFF4CAF50))
            "Reading" -> Triple<ImageVector, String, Color>(Icons.Default.Book, "Reading", Color(0xFF2196F3))
            "WantToRead" -> Triple<ImageVector, String, Color>(Icons.Default.Star, "Want To Read", Color(0xFFFFC107))
            else -> Triple<ImageVector, String, Color>(Icons.AutoMirrored.Filled.MenuBook, listType, Color.Black)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(icon, contentDescription = null, tint = color)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$label Books",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }


        if (books.isEmpty()) {
            // Display a friendly message when no books are saved
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 48.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Inbox, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No books here yet.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                    Text(
                        text = "Add some to your $label list!",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn {
                items(books.size) { index ->
                    val book = books[index]
                    BookListItem(book = book, onDelete = { toDelete ->
                        coroutineScope.launch {
                            db.bookDao().deleteBook(toDelete)
                            books = db.bookDao().getBooksByListType(listType)
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun BookListItem(book: SavedBook, onDelete: (SavedBook) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            if (!book.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(90.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    // If imageUrl is null or blank, use a default image
                    painter = painterResource(id = com.bcit.termproject2.R.drawable.book_cover),
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .width(90.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(book.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Author: ${book.author}")
                Text("Year: ${book.year ?: "N/A"}")
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Book") },
            text = { Text("Are you sure you want to delete \"${book.title}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(book)
                    showDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
