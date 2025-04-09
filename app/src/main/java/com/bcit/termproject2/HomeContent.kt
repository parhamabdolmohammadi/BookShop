package com.bcit.termproject2

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.bcit.lecture10bby.data.com.bcit.termproject2.AddToListButton
import com.bcit.lecture10bby.data.com.bcit.termproject2.BookState
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.BookRepository
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

//Home cLASS

@Composable
fun HomeContent(navController: NavController, bookRepository: BookRepository) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        LibraryCard(navController)
        Spacer(modifier = Modifier.height(16.dp))
        MyBooksCard(navController)
        Spacer(modifier = Modifier.height(16.dp))
        RecommendedSection(navController, bookRepository)
    }
}


@Composable
fun RecommendedSection(navController: NavController, bookRepository: BookRepository) {
    val bookState = viewModel {
        BookState(bookRepository)
    }

    LaunchedEffect(bookState) {
        bookState.getBooks() //suspending
    }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header row


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.book_stack),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
            )
            Text("Recommended", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }


        Box(modifier = Modifier.height(800.dp)) {
            LazyColumn {
                val pieces = bookState.bookResponse?.items

                items(pieces?.size ?: 0) {
                    val bookInfo = pieces?.get(it)?.info;
                    val bookTitle = bookInfo?.title?: "Book";
                    val publishedDate = bookInfo?.publishedDate?: "Unknown";
                    val authors = bookInfo?.authors?.joinToString(", ") ?: "Unknown Author"
                    val image = bookInfo?.imageLinks?.thumbnail?.replace("http://", "https://") ?: ""


//                    val bookAuthor = bookInfo?.authors? : "";
                    ImageBox(bookTitle, publishedDate, authors, image.toString(), navController)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}
@Composable
fun ImageBox(title: String,
             publishedDate: String,
             authors: String,
             image: String,
             navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                val encodedImage = if (image.isNotBlank()) {
                    URLEncoder.encode(image, StandardCharsets.UTF_8.toString())
                } else {
                    "null"
                }

                navController.navigate("book/${title}/${authors}/${publishedDate}/${title}/${encodedImage}")
            }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Book cover
            if (image.isNotBlank() && image.startsWith("https://")) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .width(120.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.book_cover),
                    contentDescription = null,
                    modifier = Modifier
                        .height(180.dp)
                        .width(120.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            // Scrollable content
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .verticalScroll(scrollState)
            ) {
                Column {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Title: ")
                            }
                            append(title)
                        },
                        fontSize = 14.sp,
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Published Date: ")
                        }
                        append(publishedDate)
                    })

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Authors: ")
                        }
                        append(authors)
                    })

                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(8.dp))

                    AddToListButton(
                        title = title,
                        author = authors,
                        year = publishedDate,
                        description = "",
                        imageUrl = if (image.isNotBlank()) image else null
                    )

                }
            }
        }
    }

}


@Composable
fun LibraryCard(navController: NavController) {
    var showSubBoxes = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
            .clickable {
                navController.navigate("library")// Toggle visibility
            }
            .animateContentSize()
    ) {


        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box()
            {
                Image(
                    painter = painterResource(id = R.drawable.library_main),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )

                Text("Library", fontSize = 40.sp, color = Color.White,  fontWeight = FontWeight.Bold,  modifier = Modifier.align(
                    Alignment.Center) )

            }
        }
    }
}

@Composable
fun MyBooksCard(navController: NavController) {
    var showSubBoxes = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showSubBoxes.value = !showSubBoxes.value }
            .animateContentSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray) // fallback color during image loading
    ) {
        // Set a base minimum height to allow image stretching
        val baseHeight = if (showSubBoxes.value) 500.dp else 200.dp

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(baseHeight)
        ) {
            // Background image stretched to fill
            Image(
                painter = painterResource(id = R.drawable.whitelibrary),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Content inside image box
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!showSubBoxes.value) {
                    Spacer(modifier = Modifier.height(50.dp))
                }
                Text(
                    text = "My Books",
                    fontSize = 40.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.background(Color(0xA8FFFFFF))
                )
                Spacer(modifier = Modifier.height(16.dp))

                if (showSubBoxes.value) {
                    SubBooks("Read", Color(0xA64CAF59)) {
                        navController.navigate("list/Read")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SubBooks("Reading", Color(0xA64C84AF)) {
                        navController.navigate("list/Reading")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SubBooks("Want To Read", Color(0xA6AFAA4C)) {
                        navController.navigate("list/WantToRead")
                    }
                }
            }
        }
    }
}


@Composable
fun SubBooks(label: String, color: Color, onClick: () -> Unit) {
    val icon = when (label) {
        "Read" -> Icons.Default.Done
        "Reading" -> Icons.Default.MenuBook
        "Want To Read" -> Icons.Default.BookmarkAdd
        else -> Icons.Default.MenuBook
    }

    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(imageVector = icon, contentDescription = label, tint = Color.Black)
            Text(
                text = label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


