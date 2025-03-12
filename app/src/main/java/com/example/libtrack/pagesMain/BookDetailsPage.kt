package com.example.libtrack.pagesMain

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.libtrack.backend.RetrofitBooks
import com.example.libtrack.backend.SERVER_IP
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsPage(
    bookId: Int,
    studentNumber: String,
    navController: NavHostController
){
    val scope = rememberCoroutineScope()
    var bookDetails by remember { mutableStateOf<BookDetails?>(null) }
    val context = LocalContext.current
    var isBorrowed by remember { mutableStateOf(true) }
    var isShowBorrowConfirmation by remember { mutableStateOf(false) }

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
                    .padding(10.dp)
                    .fillMaxSize()
            ) {

                Spacer(
                    modifier = Modifier
                        .height(40.dp)
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
                    ),
                    textAlign = TextAlign.Center
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

                Spacer(
                    modifier = Modifier
                        .height(25.dp)
                )

                if (bookDetails!!.availableCopies.toString() == "0"){
                    isBorrowed = false
                }

                Button(
                    onClick = {
                        isShowBorrowConfirmation = true

                    },
                    modifier = Modifier
                        .size(width = 290.dp, height = 43.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF72AF7B),
                        contentColor = Color.White
                    ),
                    enabled = isBorrowed


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

                if (isShowBorrowConfirmation) {
                    AlertDialog(
                        onDismissRequest = { isShowBorrowConfirmation = false },
                        title = {
                            Column {
                                Text(
                                    text = "Borrow Book Confirmation",
                                    fontWeight = FontWeight(600),
                                    fontSize = 20.sp
                                )
                            }
                        },
                        text = {

                            Column {
                                Text(
                                    text = "Are you sure you want to borrow this book?"
                                )

                                Row {
                                    Button(
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF72AF7B),
                                            contentColor = Color.White
                                        ),
                                        onClick = {
                                            isBorrowed = false
                                            isShowBorrowConfirmation = false
                                            // Handle Backend Insertion here
                                            scope.launch {
                                                try {
                                                    val borrowData = BorrowData(
                                                        studentId = studentNumber,
                                                        bookRequest = bookDetails?.title ?: "",
                                                        bookNumber = bookDetails?.bookNumber ?: "",
                                                        approve = "0" // Assuming 0 means pending
                                                    )
                                                    RetrofitBorrow.apiService.insertBorrowData(borrowData)
                                                    Toast.makeText(context, "Borrow request sent", Toast.LENGTH_SHORT).show()
                                                } catch (e: Exception) {
                                                    Toast.makeText(context, "Error sending request", Toast.LENGTH_SHORT).show()
                                                    Log.e("BorrowError", "Error: ${e.message}")
                                                }
                                            }
                                        },
                                    ) {
                                        Text(
                                            text = "opo",
                                            fontWeight = FontWeight(900),
                                            fontSize = 30.sp
                                        )
                                    }
                                    Button(
                                        shape = RoundedCornerShape(15.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF72AF7B),
                                            contentColor = Color.White
                                        ),
                                        onClick = {
                                            isShowBorrowConfirmation = false
                                        },
                                    ) {
                                        Text(
                                            text = "wag nalang pala",
                                            fontWeight = FontWeight(900),
                                            fontSize = 30.sp
                                        )
                                    }
                                }
                            }
                        },
                        confirmButton = {

                        }
                    )
                }
            }
        }
    }
}

data class BorrowData(
    val studentId: String,
    val bookRequest: String,
    val bookNumber: String,
    val approve: String
)

data class BorrowResponse(
    val message: String?,
    val error: String?
)

interface BorrowApiService {
    @POST("hello/borrow.php") // Replace with your PHP script's name
    suspend fun insertBorrowData(@Body borrowData: BorrowData): Call<BorrowResponse>
}

object RetrofitBorrow {
    val apiService: BorrowApiService by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_IP) // Your server IP
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BorrowApiService::class.java)
    }
}