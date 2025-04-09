package com.bcit.lecture10bby.data.com.bcit.termproject2

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bcit.termproject2.R

/**
 * Danton Soares - A01351419
 * Parham Abdolmohammadi - A01356970
 */

@Composable
fun Book(title: String?, author: String?, year: String?, description: String?, image: String?) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title ?: "Unknown Title", fontSize = 30.sp)

            if (!image.isNullOrBlank() && image.startsWith("https://")) {
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
                    append(" Author: ")
                }
                append(author ?: "Unknown")
            })

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(" Date: ")
                }
                append(year ?: "Unknown")
            })

            Text("Description:", fontSize = 24.sp, modifier = Modifier.padding(top = 16.dp))

            Text(
                text = description ?: "...",
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(20.dp)
            )

            AddToListButton(
                title = title ?: "Unknown",
                author = author ?: "Unknown Author",
                year = year,
                description = description,
                imageUrl = image ?: "",
            )
        }
    }
}
