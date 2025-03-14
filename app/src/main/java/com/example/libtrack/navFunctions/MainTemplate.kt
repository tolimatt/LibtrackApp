package com.example.libtrack.navFunctions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.libtrack.pagesMain.BooksPage
import com.example.libtrack.pagesMain.HomePage
import com.example.libtrack.backend.BarcodeDisplay
import com.example.libtrack.errorHandling.booksIconImage
import com.example.libtrack.errorHandling.codeGeneratorImage
import com.example.libtrack.errorHandling.homeIconImage
import com.example.libtrack.errorHandling.logoImage
import com.example.libtrack.errorHandling.settingsIconImage
import com.example.libtrack.pagesMain.SettingsPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    navController: NavHostController,
    studentNumber: String) {

    // Track selected item index
    var selectedIndex by remember { mutableIntStateOf(0) } // Use mutableStateOf for state

    var topBarTitle by remember { mutableStateOf("FVR - LIBRARY") } // Default

    var isShowDialog by remember { mutableStateOf(false) }

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
                    Text(text = topBarTitle, fontWeight = FontWeight(700))
                },
                actions = {
                    IconButton(onClick = {
                        isShowDialog = true
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

                    ContentScreen(selectedIndex ,studentNumber, navController)

                    if (isShowDialog) {
                        AlertDialog(
                            onDismissRequest = { isShowDialog = false },
                            title = {
                                Text(
                                    text = "Library Access Barcode Ready!",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 23.sp,
                                        fontWeight = FontWeight(600)
                                    )
                                )
                            },
                            text = {
                                Column {

                                    Text(
                                        text =  "Welcome to the Library! Your barcode has been successfully generated. Use it to access library services and resources."
                                    )

                                    Row {

                                        Spacer(modifier = Modifier.width(30.dp))
                                        BarcodeDisplay(studentNumber)
                                    }

                                    Text(studentNumber)

                                }

                            },
                            confirmButton = {
                                Button(
                                    shape = RoundedCornerShape(15.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFF72AF7B),
                                        contentColor = Color.White
                                    ),
                                    onClick = {
                                        isShowDialog = false
                                    },
                                ) {
                                    Text("Close")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ContentScreen(selectedIndex: Int,
                  studentNumber: String,
                  navController: NavHostController) {

    when(selectedIndex){
        0 -> HomePage(
            studentNumber = studentNumber,
            navController = navController
        ){ bookId ->
            navController.navigate("BookDetailsPage/$bookId/$studentNumber")
        }
        1 -> BooksPage(
            studentNumber = studentNumber,
            onBookClick = { bookId ->
            navController.navigate("BookDetailsPage/$bookId/$studentNumber")
            }
        )
        2 -> SettingsPage(
            studentNumber = studentNumber,
            navController = navController
        )
    }
}

data class BottomNavItem(
    val bottomNavIcon: Painter
)
