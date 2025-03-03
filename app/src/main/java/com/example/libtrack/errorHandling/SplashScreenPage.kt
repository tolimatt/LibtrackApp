package com.example.libtrack.errorHandling

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.libtrack.backend.SERVER_IP
import com.example.libtrack.navFunctions.Pages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


@Composable
fun SplashScreenPage(navController: NavHostController) {
    var currentProgress by remember { mutableFloatStateOf(0f) }
    var loading by remember { mutableStateOf(true) } // Start loading automatically
    val scope = rememberCoroutineScope() // Create a coroutine scope
    val context = LocalContext.current
    var serverConnected by remember { mutableStateOf(false) }


    // Start the loading process when the screen is displayed
    LaunchedEffect(Unit) {
        scope.launch {
            loadProgress { progress ->
                currentProgress = progress
            }
            loading = false // Stop loading when the coroutine finishes

            // Navigate to the Log In screen and remove the SplashScreenPage from the Back Stack
            navController.navigate(
                if(serverConnected){
                    Pages.Log_In
                } else {
                    Pages.No_Connection_Page
                }
            ) {
                popUpTo(Pages.Splash_Screen) { inclusive = true } // Remove the splash screen from the back stack
            }
        }
    }

    LaunchedEffect(true) {
        scope.launch {
            serverConnected = checkServerConnection(context)
            if (!serverConnected) {
                Toast.makeText(context, "Server connection failed.", Toast.LENGTH_LONG).show()
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
            painter = painterResource(id = logoImage),
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
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray
            )
        }
    }
}

// Iterate the progress value
suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(30) // Load Time
    }
}

private suspend fun checkServerConnection(context: android.content.Context): Boolean =
    withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(SERVER_IP) // Replace with your server URL
            .build()

        try {
            val response = client.newCall(request).execute()
            return@withContext response.isSuccessful
        } catch (e: IOException) {
            return@withContext false
        }
    }



