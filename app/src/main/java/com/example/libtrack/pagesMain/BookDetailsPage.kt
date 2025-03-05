package com.example.libtrack.pagesMain

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.libtrack.errorHandling.codeGeneratorImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsPage(bookId: Int, navController: NavHostController){
    val scope = rememberCoroutineScope()
    var bookDetails by remember { mutableStateOf<BookDetails?>(null) }
    val context = LocalContext.current

    LaunchedEffect(bookId) {
        scope.launch {
            try {
                bookDetails = RetrofitBooks.apiService.getBookDetails(bookId)
            } catch (e: Exception) {
                println("Error fetching book details: ${e.message}")
            }
        }
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Book Details", fontWeight = FontWeight(700))
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1E5128),
                    titleContentColor = Color.White
                ),

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        },
    ){ paddingValues ->
        if (bookDetails != null) {

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                Spacer(
                    modifier = Modifier
                        .height(55.dp)
                )

                AsyncImage(
                    model = bookDetails!!.imageUrl,
                    contentDescription = bookDetails!!.title,
                    modifier = Modifier
                        .size(270.dp)
                        .border(1.5.dp, Color.Black)
                )

                Spacer(
                    modifier = Modifier
                        .height(30.dp)
                )

                Text(
                    text = bookDetails!!.title,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 23.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.Default
                    )
                )

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )

                Text(
                    text = "By "+bookDetails!!.author,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily.Default
                        )
                )

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )

                Text(
                    text = bookDetails!!.description
                )

                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                )

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .size(width = 290.dp, height = 43.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF72AF7B),
                        contentColor = Color.White
                    )

                ) {
                    Text(
                        text = "Borrow Book",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.Default
                        )
                    )
                }

                Text(
                    text = bookDetails!!.availableCopies.toString()+" / "+bookDetails!!.totalCopies.toString()+" Available Copies"
                )

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )

                Button(
                    onClick = {
                        Log.d("PDF_URL_VALUE", "PDF URL: ${bookDetails!!.pdfUrl}")
                        try {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(bookDetails!!.pdfUrl)
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(context, "No PDF viewer app found.", Toast.LENGTH_SHORT).show()
                            Log.e("PDF_ERROR", "No Activity found to open PDF", e)
                        }
                    },
                    modifier = Modifier
                        .size(width = 290.dp, height = 43.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF72AF7B),
                        contentColor = Color.White
                    )

                ) {
                    Text(
                        text = "E-Book Available",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.Default
                        )
                    )
                }
            }
        }
    }
}