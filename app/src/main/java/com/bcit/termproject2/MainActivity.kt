package com.bcit.termproject2



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.SportsSoccer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bcit.termproject2.ui.theme.TermProject2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}

data class NavItem(val icon: ImageVector, val route:String)


// main content of the program
@Composable
fun MainContent() {
    //take use somewhere
    //remember the previous state(page) in back button
    //it adds transition (much better that usual) with good flow
    val navController =  rememberNavController()

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
//                Home(navController) //REMOVED

                MyCard(navController)
            }

            //destination 2
            composable("info/{name}/{image}") {
                val name = it.arguments?.getString("name")
                val image = it.arguments?.getString("image")?.toIntOrNull()
//                Info(name,image)
                Text("info", fontSize = 12.sp)
            }

            //destination3
            composable("search"){
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Search", fontSize = 30.sp)
                }
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
        NavItem(Icons.Default.Search, "search"),
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



@Composable
fun MyCard(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        ClickableBoxWithSubBoxes()
        Spacer(modifier = Modifier.height(16.dp))
        ClickableBoxWithSubBoxes2()
    }
}


@Composable
fun ClickableBoxWithSubBoxes() {
    var showSubBoxes = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize() // 👈 Make the Box size itself to its content
            .clickable {
                showSubBoxes.value = !showSubBoxes.value // Toggle visibility
            }
            .animateContentSize() // 👈 Smooth resizing animation
    ) {


        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(){
                Image(
                    painter = painterResource(id = R.drawable.soccerplayerss),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()// Optional: define size of the image
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            if (showSubBoxes.value) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)

                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    Box(modifier = Modifier.padding(10.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.messi),
                            contentDescription = null,
                            modifier = Modifier
                                .size(130.dp)

                                .clip(RoundedCornerShape(30.dp)))



                    }

                    Text("Lionel Messi", fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(200.dp))
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)

                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically) {

                    Box(modifier = Modifier.padding(10.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.ronaldo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(30.dp)))



                    }

                    Text("Ronaldo", fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(200.dp))
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)

                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    Box(modifier = Modifier.padding(10.dp)){

                        Image(
                            painter = painterResource(id = R.drawable.neymar),
                            contentDescription = null,
                            modifier = Modifier
                                .size(130.dp)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(30.dp)))



                    }

                    Text("Neymar", fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(200.dp))
                }
            }
            else {
                Text("Players", fontSize = 30.sp)
            }
        }
    }
}

@Composable
fun ClickableBoxWithSubBoxes2() {
    var showSubBoxes = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .wrapContentSize() // 👈 Make the Box size itself to its content
            .clickable {
                showSubBoxes.value = !showSubBoxes.value // Toggle visibility
            }
            .animateContentSize() // 👈 Smooth resizing animation
    ) {


        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(){
                Image(
                    painter = painterResource(id = R.drawable.teams),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()// Optional: define size of the image
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            if (showSubBoxes.value) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)

                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    Box(modifier = Modifier.padding(10.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.barcelona),
                            contentDescription = null,
                            modifier = Modifier
                                .size(130.dp)

                                .clip(RoundedCornerShape(30.dp)))



                    }

                    Text("Barcelona", fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(200.dp))
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)

                    ,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically) {

                    Box(modifier = Modifier.padding(10.dp)){
                        Image(
                            painter = painterResource(id = R.drawable.intermilan),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .fillMaxSize()
                                .clip(RoundedCornerShape(30.dp)))



                    }

                    Text("Inter Milan", fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(200.dp))
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .size(120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)

                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    Box(modifier = Modifier.padding(10.dp)){

                        Image(
                            painter = painterResource(id = R.drawable.liverpool),
                            contentDescription = null,
                            modifier = Modifier
                                .size(130.dp)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(30.dp)))



                    }

                    Text("LiverPool", fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(200.dp))
                }
            }
            else {
                Text("Teams", fontSize = 30.sp)
            }
        }
    }
}