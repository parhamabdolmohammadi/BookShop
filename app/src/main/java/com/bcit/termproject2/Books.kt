package com.bcit.lecture10bby.data.com.bcit.termproject2

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.BookRepository
import com.bcit.termproject2.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun Books(navController: NavController, bookRepository: BookRepository, query: String) {

    val bookState = viewModel {
        BookState(bookRepository)
    }

    LaunchedEffect(bookState) {
        bookState.getBook(query)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val pieces = bookState.bookResponse?.items

        items(pieces?.size ?: 0) { index ->
            val book = pieces?.get(index)
            val imageUrl = book?.info?.imageLinks?.thumbnail?.replace("http://", "https://")

            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = Color(0x4600C7FF))
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable {
                        val title = book?.info?.title ?: "Unknown"
                        val authors = book?.info?.authors?.joinToString(",") ?: "Unknown Author"
                        val date = book?.info?.publishedDate ?: "Unknown Date"
                        val description = book?.info?.description ?: "..."
                        val encodedImage = if (!imageUrl.isNullOrBlank()) {
                            URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
                        } else {
                            "null"
                        }

                        navController.navigate("book/${title}/${authors}/${date}/${description}/${encodedImage}")
                    }
            ) {
                Row {
                    // Thumbnail
                    if (!imageUrl.isNullOrBlank() && imageUrl.startsWith("https://")) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(10.dp)
                                .width(95.dp)
                                .height(135.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.book_cover),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(10.dp)
                                .width(95.dp)
                                .height(135.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Scrollable Details
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(10.dp)
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Column {
                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Title: ")
                                }
                                append(book?.info?.title ?: "Unknown")
                            }, fontSize = 16.sp)

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Authors: ")
                                }
                                append(book?.info?.authors?.joinToString(", ") ?: "Unknown")
                            }, fontSize = 16.sp)

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("Date: ")
                                }
                                append(book?.info?.publishedDate ?: "Unknown")
                            }, fontSize = 16.sp)

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                AddToListButton(
                                    title = book?.info?.title ?: "Unknown",
                                    author = book?.info?.authors?.joinToString(", ") ?: "Unknown",
                                    year = book?.info?.publishedDate ?: "Unknown",
                                    description = book?.info?.description ?: "...",
                                    imageUrl = if (!imageUrl.isNullOrBlank()) imageUrl else null
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}
