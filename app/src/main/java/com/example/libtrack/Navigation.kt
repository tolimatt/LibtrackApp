package com.example.libtrack

import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Pages.Splash_Screen) {
        composable(Pages.Log_In, enterTransition = { slideInVertically { 2000000 } }) {
            LogIn(navController)
        }
        composable(Pages.Sign_Up_Page1) {
            Page1_SU(navController)
        }
        composable(Pages.Sign_Up_Page2+"/{firstname}") { backStackEntry ->
            val firstname = backStackEntry.arguments?.getString("firstname") ?: ""
            val lastname = backStackEntry.arguments?.getString("lastname") ?: ""
            val studentId = backStackEntry.arguments?.getString("studentId") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            Page2_SU(
                navController = navController,
                firstname = firstname,
                lastname = lastname,
                studentId = studentId,
                password = password)
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
        composable(Pages.Home_Page) {
            HomePage(navController)
        }

    }
}




