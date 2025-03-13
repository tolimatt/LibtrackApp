package com.example.libtrack.pagesMain

import android.annotation.SuppressLint
import android.app.Application
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.libtrack.backend.BOOK_BORROW_URL_PATH
import com.example.libtrack.backend.BorrowViewModel
import com.example.libtrack.backend.RetrofitBooks
import com.example.libtrack.backend.SERVER_IP
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("NewApi")
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
    var isBorrowed by remember { mutableStateOf(false) }
    var isShowBorrowConfirmation by remember { mutableStateOf(false) }
    val currentDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    var decrementAvailableCopies by remember { mutableStateOf(false) }

    val deadLineDateTime = currentDateTime.plusDays(7).format(
        DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.MEDIUM
        )
    )

    val presentDateTime = currentDateTime.format(
        DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.MEDIUM
        )
    )

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
                    isBorrowed = true
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
                    enabled = !isBorrowed


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

                val decrementWhenBorrowed by remember { mutableIntStateOf(bookDetails!!.availableCopies - 1) }

                Text(
                    text = if(!decrementAvailableCopies){
                        bookDetails!!.availableCopies.toString()+" / "+bookDetails!!.totalCopies.toString()+" Available Copies"
                    } else {
                        decrementWhenBorrowed.toString()+" / "+bookDetails!!.totalCopies.toString()+" Available Copies"
                    }
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
                    ),
                    enabled = bookDetails!!.pdfUrl != null

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

                val borrowBookViewModel = remember { BorrowViewModel(application = context.applicationContext as Application) }


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
                                            isBorrowed = true
                                            isShowBorrowConfirmation = false
                                            decrementAvailableCopies = true

                                            val borrowData = BorrowData(
                                                studentId = studentNumber,
                                                bookCode = bookDetails?.bookNumber ?: "",
                                                bookTitle = bookDetails?.title ?: "",
                                                borrowDate = presentDateTime,
                                                dueDate = deadLineDateTime,
                                                status = "Borrowed"
                                            )

                                            borrowBookViewModel.borrowBook(borrowData)

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
    val bookCode: String,
    val bookTitle: String,
    val borrowDate: String,
    val dueDate: String,
    val status: String
)

data class ApiResponseBorrow(
    val status: String?,
    val error: String?
)

interface BorrowApiService {
    @POST(BOOK_BORROW_URL_PATH)
    fun insertBorrowData(@Body borrowData: BorrowData): Call<ApiResponseBorrow>
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