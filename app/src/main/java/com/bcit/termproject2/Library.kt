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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bcit.lecture10bby.data.com.bcit.termproject2.BookState
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.BookRepository
import com.bcit.termproject2.R
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Danton Soares - A01351419
 * Parham Abdolmohammadi - A01356970
 */

@Composable
fun Library(navController: NavController, bookRepository: BookRepository) {
    val backgroundPainter = painterResource(id = R.drawable.whitelibrary)

    val bookState = viewModel {
        BookState(bookRepository)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = backgroundPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.5f)
        )

        // Foreground Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SimpleSearchBar { query ->
                println("search triggered ${query}")
                val encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString())
                navController.navigate("books/$query")
                println("Search triggered: $query (encoded as: $encodedQuery)")
            }

            Text(
                "Explore By Genre",
                fontSize = 30.sp,
                modifier = Modifier.padding(20.dp),
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GenreBox("Romance", Color(0xC7FFE082), navController)
                GenreBox("Action", Color(0xC782FFF5), navController)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                GenreBox("Mystery", Color(0xC7CE93D8), navController)
                GenreBox("Fantasy", Color(0xC7A5D6A7), navController)
            }
        }
    }
}

@Composable
fun GenreBox(label: String, backgroundColor: Color, navController: NavController) {
    Box(
        modifier = Modifier
            .background(color = backgroundColor)
            .height(140.dp)
            .width(140.dp)
            .clickable {
                navController.navigate("books/$label")
            },
        contentAlignment = Alignment.Center
    ) {
        Text(label, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun SimpleSearchBar(onSearch: (String) -> Unit) {
    val bookState: BookState = viewModel()
    val searchQuery by bookState.searchFlow.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { bookState.searchFlow.value = it },
            label = { Text("Search") },
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = {
                onSearch(searchQuery)
            },
            modifier = Modifier
                .size(56.dp)
                .background(color = Color(0xFF9C27B0), shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White
            )
        }
    }
}
