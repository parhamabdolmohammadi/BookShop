package com.bcit.termproject2



//operation done
import Library
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.bcit.lecture10bby.data.com.bcit.termproject2.Book
import com.bcit.lecture10bby.data.com.bcit.termproject2.Books
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Database
import com.bcit.lecture10bby.data.com.bcit.termproject2.data.Repository
import com.bcit.termproject2.ui.theme.TermProject2Theme

class MainActivity : ComponentActivity() {
    // Declaring db objects
    private val db by lazy { Database.getInstance(applicationContext) }
    private val repo by lazy { Repository(db.bookDao()) }

    val bookRepository by lazy {
        BookRepository(client, applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TermProject2Theme {

                MainContent(bookRepository)
            }

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
            Text(text = "BestReads", fontSize = 20.sp)

        },
        navigationIcon = {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Default.Home, contentDescription = "Home")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(Icons.Outlined.Book, contentDescription = "Ball")
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

























