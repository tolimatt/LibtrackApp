package com.example.libtrack

import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


@Composable
fun Navigation() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Pages.Splash_Screen
    ) {
        composable(Pages.Log_In, enterTransition = { slideInVertically { 2000000 } }) {
            LogIn(navController)
        }
        composable(Pages.No_Connection_Page) {
            NoConnectionPage(navController)
        }
        composable(Pages.Sign_Up_Page1) {
            Page1_SU(navController)
        }
        composable(Pages.Sign_Up_Page2+"/{firstname}/{lastname}/{studentId}/{password}") { backStackEntry ->
            val firstname = backStackEntry.arguments?.getString("firstname") ?: ""
            val lastname = backStackEntry.arguments?.getString("lastname") ?: ""
            val studentId = backStackEntry.arguments?.getString("studentId") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            Page2_SU(
                navController = navController,
                firstname = firstname,
                lastname = lastname,
                studentId = studentId,
                password = password
            )
        }
        composable(Pages.Sign_Up_Complete) {
            Complete_SU(navController)
        }
        composable(Pages.Forgot_Password_Page1) {
            Page1_FP(navController)
        }
        composable(Pages.Forgot_Password_Page2) {
            Page2_FP(navController)
        }
        composable(Pages.Forgot_Password_Page3) {
            Page3_FP(navController)
        }
        composable(Pages.Forgot_Password_Complete) {
            Complete_FP(navController)
        }
        composable(Pages.Splash_Screen) {
            SplashScreenPage(navController)
        }
        composable(Pages.Book_Details_Page) {
            BookDetailsPage(navController)
        }
        composable(Pages.Main_Page, enterTransition = { slideInHorizontally { 2000000 } }) {
            MainPage(navController)
        }


    }
}







