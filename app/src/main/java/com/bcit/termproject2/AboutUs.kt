package com.bcit.termproject2

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Danton Soares - A01351419
 * Parham Abdolmohammadi - A01356970
 */

@Composable
fun AboutUsScreen() {
    val scroll = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp)
    ) {
        Text("📚 About Us", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "We developed this app as a final project for our COMP3717 class.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "BestReads is a mobile app for discovering, saving, and organizing your reading journey — powered by the Google Books API.\n\nBuilt with Kotlin, Jetpack Compose, and Room — this app delivers a smooth, modern, and personalized reading experience.\nInspired by Goodreads, but with a focus on simplicity and ease of use.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("🧠 Authors", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(8.dp))

        ClickableLink("Danton Soares", "https://github.com/Danton1")
        ClickableLink("Parham Abdolmohammadi", "https://github.com/parhamabdolmohammadi")

        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle("✨ Features")
        Feature("🔍 Book Search", "Search by keyword or genre using the Google Books API.")
        Feature("🏠 Recommended Section", "Localized recommendations based on user's region/language.")
        Feature("📖 Book Details", "Open full details: title, author, year, description, image.")
        Feature("✅ Add to List", "Quickly add to Read / Reading / Want To Read.")
        Feature("🗃️ My Books", "Access your saved lists through the home page.")
        Feature("🗑️ Remove Books", "Delete from lists with confirmation.")
        Feature("💾 Local Persistence", "Saved using Room database — no login needed.")
        Feature("🚫 Duplicate Prevention", "Avoids adding duplicates to the same list.")
        Feature("📋 Copy to Clipboard", "Copy saved books from any list (or all) via the top toolbar.")
        Feature("📲 Shareable Format", "Copied content is formatted with list names, book titles, authors, and years.")
        Feature("📣 Toast Notifications", "Get feedback when adding or copying books.")
        Feature("🎨 Custom Icon & Theme", "The app features a custom launcher icon and consistent styling using Material 3.")


        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle("🌍 Language-Agnostic")

        Text(
            text = "Thanks to Google Books API, the app adapts to your locale. If your device is in French and you search 'Romance', you'll get results in French.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle("🛠️ Tech Stack")

        TechRow("Kotlin", "Main Language")
        TechRow("Jetpack Compose", "UI Framework")
        TechRow("Room DB", "Local data storage")
        TechRow("Google Books API", "Data Source")
        TechRow("Coil", "Image Loader")
        TechRow("Material3", "UI Components")

        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle("🧪 Usage Scenarios")
        Bullet("Save and revisit previously read books")
        Bullet("Track current reads")
        Bullet("Queue up future reads")
        Bullet("Discover new books to read!")

        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle("🚀 How to Run")

        Text("1. Clone the repo\n2. Open in Android Studio\n3. Run on emulator or real device (API 26+)", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
fun Feature(title: String, desc: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(title, fontWeight = FontWeight.Bold)
        Text(desc)
    }
}

@Composable
fun TechRow(tech: String, role: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text("• $tech", fontWeight = FontWeight.Medium)
        Text("- $role", color = Color.Gray)
    }
}

@Composable
fun Bullet(text: String) {
    Text("• $text", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 4.dp))
}

@Composable
fun ClickableLink(label: String, url: String) {
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        pushStringAnnotation(tag = "URL", annotation = url)
        withStyle(style = SpanStyle(color = Color(0xFF1E88E5), fontWeight = FontWeight.Bold)) {
            append(label)
        }
        pop()
    }

    Text(
        text = annotatedString,
        modifier = Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            }
            .padding(bottom = 8.dp)
    )
}
