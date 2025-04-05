package com.bcit.termproject2



//operation done
import Library
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bcit.lecture10bby.data.com.bcit.termproject2.BookState



import com.bcit.lecture10bby.data.com.bcit.termproject2.data.BookRepository
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.client
import com.bcit.termproject2.ui.theme.TermProject2Theme
import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import coil3.compose.AsyncImage

import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    // Declaring db objects
    private val db by lazy { Database.getInstance(applicationContext) }
    private val repo by lazy { Repository(db.teamDao(), db.playerDao()) }

    val bookRepository by lazy {
        BookRepository(client)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {



            MainContent(bookRepository)

//            if (repo.getAllTeams().isEmpty()) {
//                val mockPlayers = listOf(
//                    Player(0, "Lionel Messi", "Forward", 10, 0, "Argentina", "1987-06-24", 700),
//                    Player(0, "Neymar", "Forward", 11, 0, "Brazil", "1992-02-05", 400),
//                    Player(0, "Cristiano Ronaldo", "Forward", 7, 0, "Portugal", "1985-02-05", 800)
//                )
//
//                val team = Team(
//                    id = 0,
//                    name = "Barcelona",
//                    country = "Spain",
//                    wins = 30,
//                    losses = 5,
//                    ties = 3,
//                )
//
//                repo.insertTeam(team)
//
//                mockPlayers.forEach { player ->
//                    repo.insertPlayer(player.copy(teamId = team.id))
//                }
//            }

        }
    }
}



data class NavItem(val icon: ImageVector, val route:String)


// main content of the program
@Composable
fun MainContent(bookRepository: BookRepository) {

    //take use somewhere
    //remember the previous state(page) in back button
    //it adds transition (much better that usual) with good flow
    val navController =  rememberNavController()

    val bookState = viewModel {
        BookState(bookRepository)
    }


    Scaffold (
        topBar = {
            MyTopBar(navController)
        },
        bottomBar = {
            MyBottomNav(navController)
        }
    ){
            padding ->
        //responsible for taking us somewhere
        //is a container that allows us to swap in/out destination
        NavHost(navController,
            "home",
            modifier = Modifier.padding(padding)) {


            //Navigation Graphs
            //destination 1
            //rout is id
            composable("home") {
                //what is ui that we wanna use for this destination
                HomeContent(navController, bookRepository)
//                Books(navController, bookRepository, "persia")

            }

            composable("library") {
                //what is ui that we wanna use for this destination
                Library(navController, bookRepository)
            }


//            composable("books", ) {
//                Books(navController, bookRepository)
//            }
            composable("books/{query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                println("we've got ${query}")
                Books(navController, bookRepository, query)
            }

            composable(
                route = "book/{title}/{author}/{year}/{description}/{image1}"
            ) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title") ?: ""
                val author = backStackEntry.arguments?.getString("author") ?: ""
                val year = backStackEntry.arguments?.getString("year") ?: ""
                val description = backStackEntry.arguments?.getString("description") ?: ""
                var image2 = backStackEntry.arguments?.getString("image1")?: ""
                image2 = image2.replace("http://", "https://",)

                Book(title = title, author = author, year = year, description = description, image = image2)
            }



            //destination4
            composable("more"){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("More", fontSize = 30.sp)
                }
            }
        };
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(navController: NavController) {


    CenterAlignedTopAppBar(
        title = {
            Text(text = "Colors", fontSize = 20.sp)

        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Default.Home, contentDescription = "Home")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.SportsSoccer, contentDescription = "Ball")
            }
        },
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    )
}


@Composable
fun MyBottomNav(navController: NavController) {

    val navItems = listOf(
        NavItem(Icons.Default.Home, "home"),
        NavItem(Icons.Default.Search, "library"),
        NavItem(Icons.Default.Menu, "more"),
    )

    NavigationBar {
        //get a referance to the back stack entry
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        //get the currentRoute
        val currentRoute = navBackStackEntry?.destination?.route

        //createItems
        navItems.forEach {
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route)
                },
                icon = {
                    Icon(it.icon, contentDescription = null)
                })
        }
    }
}

























