package com.example.libtrack.pagesMain

import android.annotation.SuppressLint
import android.app.Application
import android.content.ActivityNotFoundException
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.libtrack.backend.BookDetails
import com.example.libtrack.backend.BorrowData
import com.example.libtrack.backend.BorrowViewModel
import com.example.libtrack.backend.NotificationLibrary
import com.example.libtrack.backend.RetrofitBooks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsPage(
    bookId: Int,
    studentID: String,
    navController: NavHostController
){

    var lastClickTime by remember { mutableLongStateOf(0L) } // Track last click time


    val scope = rememberCoroutineScope()
    var bookDetails by remember { mutableStateOf<BookDetails?>(null) }
    val context = LocalContext.current
    var isBorrowed by remember { mutableStateOf(false) }
    var isShowBorrowConfirmation by remember { mutableStateOf(false) }
    val currentDateTime by remember { mutableStateOf(LocalDateTime.now()) }

    var decrementAvailableCopies by remember { mutableStateOf(false) }

    val borrowBookViewModel = remember { BorrowViewModel(application = context.applicationContext as Application) }

    val borrowStatus by borrowBookViewModel.getBorrowStatus().collectAsState()


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

    var isLoading by remember { mutableStateOf(true) } // State for loading


    LaunchedEffect(bookId, studentID) {
        scope.launch {
            try {
                bookDetails = RetrofitBooks.apiService.getBookDetails(bookId)
                // Call checkBorrowStatus after fetching book details
                bookDetails?.let {
                    borrowBookViewModel.checkBorrowStatus(studentID, it.bookNumber)
                }

                isLoading = false
            } catch (e: Exception) {
                println("Error fetching book details: ${e.message}")
            }
        }
    }

    // When Loading
    if (isLoading) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                strokeWidth = 8.dp,
                modifier = Modifier.width(60.dp),
                color = Color(0xFF72AF7B),
                trackColor = Color.LightGray,
            )
        }
    } else {

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
                        IconButton(onClick = {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastClickTime > 500) { // 500ms debounce
                                lastClickTime = currentTime
                                navController.popBackStack() // Navigate back
                            }
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
        ){ paddingValues ->



            if (bookDetails != null) {

                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(10.dp)
                        .fillMaxSize()
                ) {
                    item(1) {


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
                            ),
                            textAlign = TextAlign.Center
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
                            enabled = !(borrowStatus == "limit" || borrowStatus == "already_borrowed" || borrowStatus == "success" || bookDetails?.availableCopies == 0)
                        ) {
                            Text(
                                text = when (borrowStatus){
                                    "already_borrowed", "success" -> {
                                        "You Borrowed This Book Already"
                                    }
                                    "limit" -> {
                                        "3 Books Borrowed Limit Reached"
                                    }
                                    else -> {
                                        "Borrow Book"
                                    }
                                },
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
                                try {val encodedUrl = URLEncoder.encode(bookDetails!!.pdfUrl, StandardCharsets.UTF_8.toString())
                                    navController.navigate("pdf_viewer_page/$encodedUrl/${bookDetails!!.title}")
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
                            enabled = !(bookDetails!!.pdfUrl.isNullOrEmpty())

                        ) {
                            Text(
                                text = if(!(bookDetails!!.pdfUrl.isNullOrEmpty())) "E-Book Available" else "E-Book Not Available",
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
                                modifier = Modifier
                                    .size(width = 300.dp, height = 330.dp),
                                title = {
                                    Column (
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                    ){
                                        Text(
                                            text = "Borrow Book Confirmation",
                                            fontWeight = FontWeight(800),
                                            fontSize = 20.sp,
                                        )
                                    }
                                },
                                text = {

                                    Column (
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ){

                                        Text(
                                            text = "You must have a valid library account to borrow e-books.",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight(600),
                                                fontFamily = FontFamily.Default
                                            ),
                                            textAlign = TextAlign.Center
                                        )

                                        Spacer(
                                            modifier = Modifier
                                                .height(10.dp)
                                        )

                                        Text(
                                            text = " - Search the catalog for available titles.\n" +
                                                    " - Access the book online after borrowing.\n" +
                                                    " - Checkout now to start reading.\n" +
                                                    "⚠ Borrowing is disabled one week before final exams.",
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight(600),
                                                fontFamily = FontFamily.Default
                                            ),
                                            textAlign = TextAlign.Center
                                        )

                                        Spacer(
                                            modifier = Modifier
                                                .height(20.dp)
                                        )

                                        Row {

                                            Button(
                                                shape = RoundedCornerShape(15.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color.Gray.copy(alpha = 0.2f),
                                                    contentColor = Color(0xFF72AF7B)
                                                ),
                                                onClick = {
                                                    isShowBorrowConfirmation = false
                                                },
                                            ) {
                                                Text(
                                                    text = "Cancel",
                                                    fontWeight = FontWeight(500),
                                                    fontSize = 20.sp,
                                                )
                                            }

                                            Spacer(
                                                modifier = Modifier
                                                    .width(18.dp)
                                            )

                                            Button(
                                                shape = RoundedCornerShape(15.dp),
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = Color(0xFF72AF7B),
                                                    contentColor = Color.White
                                                ),
                                                onClick = {
                                                    isBorrowed = false
                                                    isShowBorrowConfirmation = false
                                                    decrementAvailableCopies = true


                                                    NotificationLibrary(context).showNotificationBorrowed(bookDetails!!.title, deadLineDateTime)
                                                    NotificationLibrary(context).showNotificationReturned(bookDetails!!.title, deadLineDateTime)

                                                    val borrowData = BorrowData(
                                                        studentId = studentID,
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
                                                    text = "Confirm",
                                                    fontWeight = FontWeight(500),
                                                    fontSize = 20.sp
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
    }


}


@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfViewer(bookTitle: String, pdfUrl: String, navController: NavHostController) {
    val context = LocalContext.current
    var pdfBitmapList by remember { mutableStateOf<List<Bitmap>?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val activity = LocalContext.current as ComponentActivity

    // Disable Screenshots and deny screen recording
    LaunchedEffect(Unit) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    LaunchedEffect(pdfUrl) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                val file = downloadPdf(pdfUrl, context.cacheDir)

                // Check if file is null (either download failed or too large)
                if (file == null) {
                    error = "PDF file is too large or failed to download."
                } else {
                    pdfBitmapList = renderPdf(file)
                }
            } catch (e: Exception) {
                error = "Error: ${e.message}"
            } finally {
                loading = false
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = bookTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis, // for dot dot dot
                        fontWeight = FontWeight(600)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1E5128),
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (loading) {

                CircularProgressIndicator(
                    modifier = Modifier.width(60.dp),
                    color = Color(0xFF72AF7B),
                    trackColor = Color.LightGray,
                )

            } else if (error != null) {
                Text(text = error!!, color = Color.Red)
            } else if (pdfBitmapList != null) {
                pdfBitmapList?.forEach { bitmap ->
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "PDF Page",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
    }
}

private suspend fun downloadPdf(pdfUrl: String, cacheDir: File): File? =
    withContext(Dispatchers.IO) {
        try {
            val url = URL(pdfUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            // Check content length (file size)
            val fileSize = connection.contentLength // Size in bytes
            val maxFileSize = 50 * 1024 * 1024 // 50MB limit

            if (fileSize > maxFileSize) {
                Log.e("PDF_ERROR", "PDF file is too large: ${fileSize / (1024 * 1024)} MB")
                return@withContext null
            }

            val inputStream = connection.inputStream
            val file = File(cacheDir, "temp.pdf")
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.close()
            inputStream.close()
            return@withContext file
        } catch (e: Exception) {
            Log.e("PDF_ERROR", "Error downloading PDF: ${e.message}")
            return@withContext null
        }
    }


private suspend fun renderPdf(pdfFile: File): List<Bitmap> = withContext(Dispatchers.IO) {

    val bitmaps = mutableListOf<Bitmap>()

    try {

        val parcelFileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(parcelFileDescriptor)

        for (i in 0 until pdfRenderer.pageCount) {

            val page = pdfRenderer.openPage(i)
            val bitmap = Bitmap.createBitmap(
                page.width,
                page.height,
                Bitmap.Config.ARGB_8888
            )
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmaps.add(bitmap)
            page.close()
        }

        pdfRenderer.close()
        parcelFileDescriptor.close()

    } catch (e: Exception) {
        e.printStackTrace()
    }
    return@withContext bitmaps
}