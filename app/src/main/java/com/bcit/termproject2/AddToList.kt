package com.bcit.lecture10bby.data.com.bcit.termproject2

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Database
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.SavedBook
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddToListButton(
    title: String,
    author: String,
    year: String?,
    description: String?,
    imageUrl: String?
) {
    val context = LocalContext.current
    val db = Database.getInstance(context)
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showDialog = true },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009644)),
        border = BorderStroke(1.dp, Color.DarkGray),
        modifier = Modifier
            .padding(8.dp,0.dp,8.dp,8.dp)
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
                    Text("Add to List", modifier = Modifier.padding(start = 8.dp), color = Color.Green, fontWeight = FontWeight.Bold)
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
                                    title = title,
                                    author = author,
                                    year = year,
                                    description = description ?: "",
                                    imageUrl = imageUrl,
                                    listType = listType,
                                    genre = null
                                )
                                scope.launch {
                                    val exists = db.bookDao().getBooksByListType(listType)
                                        .any { it.title == savedBook.title && it.author == savedBook.author }

                                    showDialog = false

                                    if (!exists) {
                                        db.bookDao().insertBook(savedBook)
                                        withContext(kotlinx.coroutines.Dispatchers.Main) {
                                            Toast.makeText(context, "Added to $label", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        withContext(kotlinx.coroutines.Dispatchers.Main) {
                                            Toast.makeText(context, "Already in $label", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
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
