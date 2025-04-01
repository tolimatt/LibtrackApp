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
import com.example.libtrack.errorHandling.AboutUsLoadingScreen
import com.example.libtrack.errorHandling.ChangePasswordLoadingScreen
import com.example.libtrack.errorHandling.HistoryLoadingScreen
import com.example.libtrack.errorHandling.LoadingScreen
import com.example.libtrack.errorHandling.NoConnectionPage
import com.example.libtrack.errorHandling.PersonalInfoLoadingScreen
import com.example.libtrack.errorHandling.ServicesLoadingScreen
import com.example.libtrack.errorHandling.SplashScreenPage
import com.example.libtrack.errorHandling.StaffTeamLoadingScreen
import com.example.libtrack.pagesMain.AboutUsPage
import com.example.libtrack.pagesMain.BookDetailsPage
import com.example.libtrack.pagesMain.BooksPage
import com.example.libtrack.pagesMain.HistoryPage
import com.example.libtrack.pagesMain.HomePage
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
        startDestination = "splash_screen_page"
    ) {
        composable("splash_screen_page") {
            SplashScreenPage(
                navController = navController
            )
        }
        composable("log_in_page", enterTransition = { slideInVertically { 2000000 } }) {
            LogIn(
                navController = navController
            )
        }
        composable("no_connection_page") {
            NoConnectionPage(
                navController = navController
            )
        }
        composable("sign_up_page1") {
            Page1_SU(
                navController = navController
            )
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
        composable("sign_up_complete_page") {
            Complete_SU(navController)
        }
        composable("sign_up_error_page") {
            Error_SU(navController)
        }
        composable("forgot_password_loading_page1") {
            ChangePasswordLoadingScreen(navController)
        }
        composable("forgot_password_page1") {
            Page1_FP(navController)
        }
        composable("forgot_password_page2/{email}") { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            Page2_FP(navController, email) // Pass the email
        }
        composable("forgot_password_page3/{email}") {backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            Page3_FP(navController, email)
        }
        composable("forgot_password_complete_page") {
            Complete_FP(navController)
        }
        composable("personal_info_loading_page/{studentID}") { backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            PersonalInfoLoadingScreen(navController, studentID)
        }
        composable("personal_info_page/{studentID}") { backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            PersonalInfoPage(
                studentID = studentID,
                navController = navController
            )
        }
        composable("history_loading_page/{studentNumber}") {backStackEntry ->
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""
            HistoryLoadingScreen(
                studentID = studentNumber,
                navController = navController
            )
        }
        composable("history_page/{studentNumber}") {backStackEntry ->
            val studentNumber = backStackEntry.arguments?.getString("studentNumber") ?: ""
            HistoryPage(
                studentID = studentNumber,
                navController = navController
            )
        }
        composable("staff_team_loading_page") {
            StaffTeamLoadingScreen(navController)
        }
        composable("staff_team_page") {
            StaffTeamPage(navController)
        }
        composable("services_loading_page") {
            ServicesLoadingScreen(navController)
        }
        composable("services_page") {
            ServicesPage(navController)
        }
        composable("about_us_loading_page") {
            AboutUsLoadingScreen(navController)
        }
        composable("about_us_page") {
            AboutUsPage(navController)
        }
        composable("main_page/{studentID}") { backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            MainPage(
                navController = navController,
                studentID = studentID
            )
        }
        composable("home_page/{studentID") { backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            HomePage(
                studentID = studentID,
                navController = navController
            ){ bookId -> navController.navigate("book_details_page/$bookId/$studentID") }
        }
        composable("books_page/{studentID}") {backStackEntry ->
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            BooksPage(
                studentID = studentID,
                navController = navController,
                onBookClick = { bookId ->
                navController.navigate("book_details_page/$bookId/$studentID")
                }
            )
        }
        composable("loading_page/{bookId}/{studentID}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull() ?: 0
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            LoadingScreen(navController, bookId, studentID)
        }
        composable("book_details_page/{bookId}/{studentID}", arguments = listOf(navArgument("bookId") { type = NavType.IntType })) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
            val studentID = backStackEntry.arguments?.getString("studentID") ?: ""
            BookDetailsPage(bookId = bookId, studentID = studentID, navController = navController)
        }
        composable("pdf_viewer_page/{pdfUrl}/{bookTitle}") { backStackEntry ->
            val bookTitle = backStackEntry.arguments?.getString("bookTitle") ?: ""
            val pdfUrl = backStackEntry.arguments?.getString("pdfUrl")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } ?: ""
            PdfViewer(bookTitle, pdfUrl,navController)
        }
    }
}