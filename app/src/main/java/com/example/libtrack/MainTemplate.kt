package com.example.libtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(navController: NavHostController) {

    // Track selected item index
    val selectedIndex = remember { mutableIntStateOf(0) } // Use mutableStateOf for state

    val topBarTitle = remember { mutableStateOf("FVR - LIBRARY") } // Default

    // Bottom Navigation Bar
    val bottomNavItemList = listOf(
        BottomNavItem(painterResource(id = R.drawable.unselect_home), painterResource(id = R.drawable.select_home)),
        BottomNavItem(painterResource(id = R.drawable.unselect_book), painterResource(id = R.drawable.select_book)),
        BottomNavItem(painterResource(id = R.drawable.unselect_settings), painterResource(id = R.drawable.select_settings)),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = topBarTitle.value, fontWeight = FontWeight(700))
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.id_barcode),
                            contentDescription = "Scan QR Code",
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
                    .height(105.dp)
                    .background(Color.White)
            ) {
                bottomNavItemList.forEachIndexed { index, bottomNavItem ->
                    NavigationBarItem(
                        selected = selectedIndex.intValue == index, // Use selectedIndex.value to access state
                        onClick = { selectedIndex.intValue = index
                            topBarTitle.value = when (index) { // Will change Top bar titles when pressed in bottom nav bar
                                0 -> "FVR - LIBRARY"
                                1 -> "Book Collections"
                                2 -> "Settings"
                                else -> ""
                            } },
                        icon = {
                            Icon(
                                painter = if (selectedIndex.intValue == index) bottomNavItem.selectedIcon else bottomNavItem.unselectedIcon,
                                tint = Color.Unspecified,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(25.dp)
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
                        painter = painterResource(R.drawable.logo),
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
                    ContentScreen(selectedIndex.intValue)
                }

            }

        }
    }
}

@Composable
fun ContentScreen(selectedIndex: Int) {
    when(selectedIndex){
        0 -> HomePage()
        1 -> BooksPage()
        2 -> SettingsPage()
    }
}

data class BottomNavItem(
    val unselectedIcon: Painter,
    val selectedIcon: Painter
)
