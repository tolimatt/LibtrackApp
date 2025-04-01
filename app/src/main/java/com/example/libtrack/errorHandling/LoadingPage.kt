package com.example.libtrack.errorHandling

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navController: NavController, bookId: Int, studentID: String) {
    LaunchedEffect(Unit) {
        delay(2000) // Simulating a 2-second loading delay
        navController.navigate("book_details_page/$bookId/$studentID"){
            popUpTo("loading_page/$bookId/$studentID") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

        }
    }
}

@Composable
fun PersonalInfoLoadingScreen(navController: NavController, studentID: String) {
    LaunchedEffect(Unit) {
        delay(2000) // Simulate loading time
        navController.navigate("personal_info_page/$studentID"){
            popUpTo("personal_info_loading_page/$studentID") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )

            Spacer(
                modifier = Modifier.height(16.dp)

            )
        }
    }
}

@Composable
fun ChangePasswordLoadingScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Simulate loading time
        navController.navigate("forgot_password_page1"){
            popUpTo("forgot_password_loading_page1") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )

            Spacer(
                modifier = Modifier.height(16.dp)

            )
        }
    }
}

@Composable
fun HistoryLoadingScreen(navController: NavController, studentID: String) {
    LaunchedEffect(Unit) {
        delay(2000) // Simulate loading time
        navController.navigate("history_page/$studentID"){
            popUpTo("history_loading_page/$studentID") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )

            Spacer(
                modifier = Modifier.height(16.dp)

            )
        }
    }
}

@Composable
fun StaffTeamLoadingScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Simulate loading time
        navController.navigate("staff_team_page"){
            popUpTo("staff_team_loading_page") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )

            Spacer(
                modifier = Modifier.height(16.dp)

            )
        }
    }
}

@Composable
fun ServicesLoadingScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Simulate loading time
        navController.navigate("services_page"){
            popUpTo("services_loading_page") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )

            Spacer(
                modifier = Modifier.height(16.dp)

            )
        }
    }
}

@Composable
fun AboutUsLoadingScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Simulate loading time
        navController.navigate("about_us_page"){
            popUpTo("about_us_loading_page") {
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )

            Spacer(
                modifier = Modifier.height(16.dp)

            )
        }
    }
}