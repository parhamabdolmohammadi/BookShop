package com.bcit.termproject2

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.bcit.lecture10bby.data.com.bcit.termproject2.BookState
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.BookRepository
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Database
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.SavedBook
import kotlinx.coroutines.launch

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
        RecommendedSection(bookRepository)
    }
}


@Composable
fun RecommendedSection(bookRepository: BookRepository) {
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
                painter = painterResource(id = R.drawable.recommened_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(75.dp)
                    .height(75.dp)
            )
            Text("Recommended", fontSize = 20.sp)
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
                    ImageBox(bookTitle, publishedDate, authors, image.toString())
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
fun ImageBox(title: String, publishedDate: String, authors: String, image: String) {
    var showDialog by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Row {
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
            val scrollState = rememberScrollState()

            Column {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Title: ")
                        }
                        append(title)
                    },
                    fontSize = 14.sp,
                    modifier = Modifier.verticalScroll(scrollState)
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

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { showDialog = true },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF009644)
                        ),
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add")
                        Text("Add", modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
    if (showDialog) {
        AddToListDialog(
            title = title,
            author = authors,
            year = publishedDate,
            imageUrl = image,
            onDismiss = { showDialog = false }
        )
    }
}

@Composable
fun AddToListDialog(
    title: String,
    author: String,
    year: String?,
    imageUrl: String?,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val db = Database.getInstance(context)
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = {Icon(Icons.Default.LibraryAdd, contentDescription = "Add", tint = Color(0xFF009644))
            Text("Add to List", modifier = Modifier.padding(start = 30.dp), color = Color.Green, fontWeight = FontWeight.Bold) },
        text = {
            Column {
                listOf("Read", "Reading", "WantToRead").forEach { listType ->
                    Button(
                        onClick = {
                            val savedBook = SavedBook(
                                title = title,
                                author = author,
                                year = year,
                                description = "",
                                imageUrl = imageUrl,
                                listType = listType,
                                genre = null
                            )

                            scope.launch {
                                db.bookDao().insertBook(savedBook)
                                onDismiss()
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text("Add to $listType")
                    }
                }
            }
        }
    )
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
                        .fillMaxWidth()// Optional: define size of the image
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
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


