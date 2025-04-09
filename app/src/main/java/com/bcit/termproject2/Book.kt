package com.bcit.lecture10bby.data.com.bcit.termproject2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import coil3.compose.AsyncImage
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Database
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.SavedBook
import com.bcit.termproject2.R
import kotlinx.coroutines.launch


@Composable
fun Book(title: String?, author: String?, year: String?, description: String?, image: String? ) {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(title!!, fontSize = 35.sp)

        if (image != null && image.isNotBlank() && image.startsWith("https://")) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .width(195.dp)
                    .height(295.dp),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.book_cover),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .width(195.dp)
                    .height(295.dp),
                contentScale = ContentScale.Crop
            )
        }

        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(" Author : ")
            }
            append(author)
        })

        Text(buildAnnotatedString {
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(" Date : ")
            }
            append(year)
        })

        Text("Description:", fontSize = 35.sp)
        Row(horizontalArrangement = Arrangement.Start) {

            Text(description ?: "...", Modifier.padding(20.dp), fontSize = 20.sp)
        }
        val context = LocalContext.current
        val db = Database.getInstance(context)
        val scope = rememberCoroutineScope()
        var showDialog by remember { mutableStateOf(false) }

        Button(
            onClick = { showDialog = true },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF009644) // Green like Recommended
            ),
            border = BorderStroke(1.dp, Color.DarkGray),
            modifier = Modifier
                .padding(8.dp)
                .height(70.dp)
                .width(170.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
            Text("Add", modifier = Modifier.padding(start = 8.dp))
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LibraryAdd, contentDescription = "Add", tint = Color(0xFF009644))
                        Text("Add to List", modifier = Modifier.padding(start = 8.dp), color = Color(0xFF009644), fontWeight = FontWeight.Bold)
                    }
                },
                text = {
                    Column {
                        listOf("Read", "Reading", "WantToRead").forEach { listType ->
                            val label = when (listType) {
                                "WantToRead" -> "Want To Read"
                                else -> listType
                            }

                            Button(
                                onClick = {
                                    val savedBook = SavedBook(
                                        title = title ?: "",
                                        author = author ?: "",
                                        year = year ?: "",
                                        description = description ?: "",
                                        imageUrl = image,
                                        listType = listType,
                                        genre = null
                                    )
                                    scope.launch {
                                        db.bookDao().insertBook(savedBook)
                                        showDialog = false
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Text("Add to $label")
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }


    }

}







