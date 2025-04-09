package com.bcit.lecture10bby.data.com.bcit.termproject2

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Database

@Composable
fun AddToListButton(
    title: String,
    author: String,
    year: String?,
    description: String?,
    imageUrl: String?,
    snackbarHostState: SnackbarHostState = rememberSnackbarHostState()
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
                                    title = title,
                                    author = author,
                                    year = year,
                                    description = description ?: "",
                                    imageUrl = imageUrl,
                                    listType = listType,
                                    genre = null
                                )
                                scope.launch {
                                    db.bookDao().insertBook(savedBook)
                                    showDialog = false
                                    snackbarHostState.showSnackbar("Added to $label list!")
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

fun rememberSnackbarHostState() {
    TODO("Not yet implemented")
}
