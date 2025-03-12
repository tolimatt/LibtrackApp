package com.example.libtrack.navFunctions

import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.libtrack.authentication.Complete_FP
import com.example.libtrack.authentication.Complete_SU
import com.example.libtrack.authentication.Error_SU
import com.example.libtrack.authentication.LogIn
import com.example.libtrack.authentication.Page1_FP
import com.example.libtrack.authentication.Page1_SU
import com.example.libtrack.authentication.Page2_FP
import com.example.libtrack.authentication.Page2_SU
import com.example.libtrack.authentication.Page3_FP
import com.example.libtrack.errorHandling.AccountErrorPage
import com.example.libtrack.pagesMain.BookDetailsPage
import com.example.libtrack.pagesMain.HomePage
import com.example.libtrack.errorHandling.NoConnectionPage
import com.example.libtrack.errorHandling.SplashScreenPage
import com.example.libtrack.pagesMain.BooksPage

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
        composable(Pages.Account_Error_Page) {
            AccountErrorPage(navController)
        }
        composable("sign_up_page1") {
            Page1_SU(navController)
        }
        composable("sign_up_page2/{firstname}/{lastname}/{studentId}/{password}") { backStackEntry ->
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
        composable("sign_up_complete") {
            Complete_SU(navController)
        }
        composable("sign_up_error") {
            Error_SU(navController)
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
        composable(Pages.Main_Page +"/{studentNumber}") { backStackEntry ->
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""
            MainPage(
                navController = navController,
                studentNumber = studentNumber
            )
        }
        composable("HomePage/{studentNumber}") { backStackEntry ->
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""
            HomePage(
                studentNumber = studentNumber,
                navController = navController
            ){ bookId -> navController.navigate("BookDetailsPage/$bookId/$studentNumber") }
        }
        composable("BooksPage/{studentNumber}") {backStackEntry ->
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""
            BooksPage(studentNumber = studentNumber, onBookClick = { bookId ->
                navController.navigate("BookDetailsPage/$bookId/$studentNumber")
            })
        }
        composable(Pages.Book_Details_Page + "/{bookId}/{studentNumber}", arguments = listOf(navArgument("bookId") { type = NavType.IntType })) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""

            BookDetailsPage(bookId, studentNumber,navController)
        }
    }
}







