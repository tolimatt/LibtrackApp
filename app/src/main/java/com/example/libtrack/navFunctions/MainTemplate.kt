package com.example.libtrack.navFunctions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.libtrack.backend.BarcodeDisplay
import com.example.libtrack.errorHandling.booksIconImage
import com.example.libtrack.errorHandling.codeGeneratorImage
import com.example.libtrack.errorHandling.homeIconImage
import com.example.libtrack.errorHandling.logoImage
import com.example.libtrack.errorHandling.settingsIconImage
import com.example.libtrack.pagesMain.BooksPage
import com.example.libtrack.pagesMain.HomePage
import com.example.libtrack.pagesMain.SettingsPage
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavHostController,
    studentID: String) {

    // Track selected item index
    var selectedIndex by remember { mutableIntStateOf(0) } // Use mutableStateOf for state

    var topBarTitle by remember { mutableStateOf("FVR - LIBRARY") } // Default

    var isShowDialog by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }

    // Bottom Navigation Bar
    val bottomNavItemList = listOf(
        BottomNavItem(painterResource(id = homeIconImage)),
        BottomNavItem(painterResource(id = booksIconImage)),
        BottomNavItem(painterResource(id = settingsIconImage))
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = topBarTitle,
                        fontWeight = FontWeight(700)
                    )
                },
                actions = {
                    IconButton(onClick = {
                        isLoading = true
                        isShowDialog = false
                    }) {
                        Icon(
                            painter = painterResource(id = codeGeneratorImage),
                            contentDescription = "Generate QR Code",
                            tint = Color.White,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1E5128),
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .height(85.dp)
                    .background(Color.White)
            ) {
                bottomNavItemList.forEachIndexed { index, bottomNavItem ->
                    NavigationBarItem(
                        selected = selectedIndex == index, // Use selectedIndex.value to access state
                        onClick = { selectedIndex = index
                            topBarTitle = when (index) { // Will change Top bar titles when pressed in bottom nav bar
                                0 -> "FVR - LIBRARY"
                                1 -> "Book Collections"
                                2 -> "Settings"
                                else -> ""
                            } },
                        icon = {
                            Icon(
                                painter = bottomNavItem.bottomNavIcon,
                                tint = if (selectedIndex == index) Color(0xFF72AF7B) else Color.Unspecified,
                                contentDescription = "Bottom Navigation Icon",
                                modifier = Modifier
                                    .size( if (selectedIndex == index) 40.dp else 35.dp)
                            )
                        },
                    )
                }
            }
        }
    ) { paddingValues ->

        if (isLoading) {
            LaunchedEffect(Unit) {
                delay(1000)
                isLoading = false
                isShowDialog = true
            }
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RectangleShape,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    Image(
                        painter = painterResource(logoImage),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(250.dp)
                            .align(Alignment.Center)
                    )

                    // Overlay with transparency
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF72AF7B).copy(alpha = 0.8f)) // Adjust the alpha for transparency
                    )

                    ContentScreen(
                        selectedIndex = selectedIndex ,
                        studentID = studentID,
                        navController = navController
                    )

                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = 0.2f)), // Semi-transparent background
                            contentAlignment = Alignment.Center  // Centers the content inside the Box
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 8.dp,
                                modifier = Modifier.size(60.dp),
                                color = Color(0xFF72AF7B),
                                trackColor = Color.LightGray
                            )
                        }
                    }


                    if (isShowDialog) {
                        isLoading = false

                        Dialog(
                            onDismissRequest = { isShowDialog = false }
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(400.dp,560.dp),
                                shape = RoundedCornerShape(20.dp), // Rounded corners
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Spacer(
                                        modifier = Modifier.height(10.dp)
                                    )
                                    Text(
                                        text = "Library Access Barcode Ready!",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 23.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(
                                        modifier = Modifier.height(20.dp)
                                    )

                                    Text(
                                        text = "Welcome to the Library! Your barcode has been successfully generated. Use it to access library services and resources.",
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight(500)
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    BarcodeDisplay(studentID)

                                    Text(
                                        text = studentID,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        modifier = Modifier
                                            .offset(0.dp, (-20).dp)
                                    )

                                    Button(
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF72AF7B),
                                            contentColor = Color.White
                                        ),
                                        onClick = {
                                            isShowDialog = false
                                        }
                                    ) {
                                        Text("Close")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ContentScreen(selectedIndex: Int,
                  studentID: String,
                  navController: NavHostController) {

    when(selectedIndex){
        0 -> HomePage(
            studentID = studentID,
            navController = navController
        ){ bookId ->
            navController.navigate("book_details_page/$bookId/$studentID")
        }
        1 -> BooksPage(
            studentID = studentID,
            navController = navController,
            onBookClick = { bookId ->
            navController.navigate("book_details_page/$bookId/$studentID")
            }
        )
        2 -> SettingsPage(
            studentID = studentID,
            navController = navController
        )
    }
}

data class BottomNavItem(
    val bottomNavIcon: Painter
)
