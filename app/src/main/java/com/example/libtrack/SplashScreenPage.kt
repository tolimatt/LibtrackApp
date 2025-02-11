package com.example.libtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SplashScreenPage(navController: NavHostController) {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(true) } // Start loading automatically
    val scope = rememberCoroutineScope() // Create a coroutine scope

    // Start the loading process when the screen is displayed
    LaunchedEffect(Unit) {
        scope.launch {
            loadProgress { progress ->
                currentProgress = progress
            }
            loading = false // Stop loading when the coroutine finishes

            // Navigate to the Log In screen and remove the SplashScreenPage from the back stack
            navController.navigate(Pages.Log_In) {
                popUpTo(Pages.Splash_Screen) { inclusive = true } // Remove the splash screen from the back stack
            }
        }
    }

    // Display the loading progress UI
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo Loading",
            modifier = Modifier
                .size(230.dp)
                .clip(CircleShape)
        )

        Spacer(
            modifier = Modifier
                .height(90.dp)
        )

        if (loading) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier.width(300.dp),
            )
        }
    }
}

// Iterate the progress value
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(1/** 20 for original**/) // Milliseconds to Load
    }
}




