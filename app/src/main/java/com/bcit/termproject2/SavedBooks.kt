package com.bcit.lecture10bby.data.com.bcit.termproject2

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

        Text(
            text = "$listType Books",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(books) { book ->
                BookListItem(book = book)
            }
        }
    }
}

@Composable
fun BookListItem(book: SavedBook) {
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
                    painter = painterResource(id = com.bcit.termproject2.R.drawable.book_cover), // use your default image here
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
        }
    }
}
