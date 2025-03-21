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
import com.example.libtrack.pagesMain.BookDetailsPage
import com.example.libtrack.pagesMain.HomePage
import com.example.libtrack.errorHandling.NoConnectionPage
import com.example.libtrack.errorHandling.SplashScreenPage
import com.example.libtrack.pagesMain.AboutUsPage
import com.example.libtrack.pagesMain.BooksPage
import com.example.libtrack.pagesMain.HistoryPage
import com.example.libtrack.pagesMain.PdfViewer
import com.example.libtrack.pagesMain.PersonalInfoPage
import com.example.libtrack.pagesMain.ServicesPage
import com.example.libtrack.pagesMain.StaffTeamPage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Pages.Splash_Screen
    ) {
        composable(Pages.Splash_Screen) {
            SplashScreenPage(navController)
        }
        composable(Pages.Log_In, enterTransition = { slideInVertically { 2000000 } }) {
            LogIn(navController)
        }
        composable(Pages.No_Connection_Page) {
            NoConnectionPage(navController)
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
                studentID = studentId,
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
        composable("${Pages.Forgot_Password_Page2}/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            Page2_FP(navController, email) // Pass the email
        }
        composable(Pages.Forgot_Password_Page3+"/{email}") {backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            Page3_FP(navController, email)
        }
        composable(Pages.Forgot_Password_Complete) {
            Complete_FP(navController)
        }
        composable("personal_info_page/{studentNumber}") {backStackEntry ->
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""
            PersonalInfoPage(
                studentID = studentNumber,
                navController = navController)
        }
        composable("history_page/{studentNumber}") {backStackEntry ->
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""
            HistoryPage(
                studentID = studentNumber,
                navController = navController
            )
        }
        composable("staff_team_page") {
            StaffTeamPage(navController)
        }
        composable("services_page") {
            ServicesPage(navController)
        }
        composable("about_us_page") {
            AboutUsPage(navController)
        }
        composable(Pages.Main_Page +"/{studentID}") { backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            MainPage(
                navController = navController,
                studentID = studentID
            )
        }
        composable("HomePage/{studentNumber}") { backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            HomePage(
                studentID = studentID,
                navController = navController
            ){ bookId -> navController.navigate("BookDetailsPage/$bookId/$studentID") }
        }
        composable("BooksPage/{studentID}") {backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            BooksPage(
                studentID = studentID,
                navController = navController,
                onBookClick = { bookId ->
                navController.navigate("BookDetailsPage/$bookId/$studentID")
                }
            )
        }
        composable(Pages.Book_Details_Page + "/{bookId}/{studentID}", arguments = listOf(navArgument("bookId") { type = NavType.IntType })) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            BookDetailsPage(
                bookId = bookId,
                studentID = studentID,
                navController = navController
            )
        }
        composable("pdfViewer/{pdfUrl}") { backStackEntry ->
            val pdfUrl = backStackEntry.arguments?.getString("pdfUrl")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } ?: ""

            PdfViewer(pdfUrl)
        }
    }
}







