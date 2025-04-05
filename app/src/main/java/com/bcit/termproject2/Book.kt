package com.bcit.lecture10bby.data.com.bcit.termproject2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bcit.termproject2.R


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

//        Image(
//            painter = painterResource(R.drawable.book_cover),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .padding(10.dp)
//                .width(195.dp)
//                .height(295.dp)
//        )
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
        Button(
            onClick = { println("Hello") },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF9C27B0)
            ),
            border = BorderStroke(1.dp, Color.DarkGray),
            modifier = Modifier.padding(8.dp).height(70.dp).width(170.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
            Text("Add", modifier = Modifier.padding(start = 8.dp))
        }

    }

}







