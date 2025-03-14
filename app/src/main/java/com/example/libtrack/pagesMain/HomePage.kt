package com.example.libtrack.pagesMain

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.libtrack.backend.HOME_PAGE_URL_PATH
import com.example.libtrack.backend.SERVER_IP
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@SuppressLint("SuspiciousIndentation")
@Composable
fun HomePage(studentNumber: String,
             navController: NavHostController,
             onBookClick: (Any) -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var firstName by remember { mutableStateOf<String?>("") }
    var department by remember { mutableStateOf<String?>("") }
    var validAccount by remember { mutableStateOf<String?>("") }


    var books by remember { mutableStateOf(listOf<Book>()) }
    var isLoading by remember { mutableStateOf(true) } // State for loading

    // Later
    if (validAccount == "0"){

        Text("Lmao Banned")// Navigate to Error Page
    } else {

        LaunchedEffect(true) {
            scope.launch {
                try {
                    books = RetrofitBooks.apiService.getBooks()
                    isLoading = false // Set loading to false when books are fetched
                } catch (e: Exception) {
                    // Handle error (e.g., show a message)
                    println("Error fetching books: ${e.message}")
                    isLoading = false // Set loading to false even on error
                }
            }
        }

        LaunchedEffect(studentNumber) {

            scope.launch {
                try {
                    val response = RetrofitAccountName.apiService.getFirstName(studentNumber)
                    response.enqueue(object : Callback<UserDetails> {
                        override fun onResponse(
                            call: Call<UserDetails>,
                            response: Response<UserDetails>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                firstName = response.body()!!.firstName
                                department = response.body()!!.department
                                validAccount = response.body()!!.status.toString()
                            } else {
                                // Log the error response
                                Log.e("HomePage", "Error response code: ${response.code()}")
                                Log.e("HomePage", "Error response body: ${response.errorBody()?.string()}")
                                Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                            // Log the network error
                            Log.e("HomePage", "Network error: ${t.message}")
                            Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })

                } catch (e: Exception) {
                    // Log any unexpected exceptions
                    Log.e("HomePage", "Unexpected error: ${e.message}")
                    Toast.makeText(context, "An unexpected error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
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
                    modifier = Modifier.width(60.dp),
                    color = Color(0xFF72AF7B),
                    trackColor = Color.LightGray,
                )
            }
        } else {
            LazyColumn (
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(1){

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    if (firstName != null){
                        Text(
                            modifier = Modifier
                                .offset(3.dp, 0.dp),
                            text = "Hello $firstName!",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 25.sp,
                                fontWeight = FontWeight(700)
                            )
                        )
                    } else {
                        Row{
                            Spacer(
                                modifier = Modifier.width(50.dp)
                            )
                            CircularProgressIndicator(
                                modifier = Modifier.size(29.dp),
                                color = Color(0xFF72AF7B),
                                trackColor = Color.LightGray,
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        modifier = Modifier
                            .offset(3.dp, 0.dp),
                        text = "Let's start reading!",
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Color(0xFF727D83)
                        )
                    )

                    Spacer(
                        modifier = Modifier.height(25.dp)
                    )

                    // Top Rated Books
                    Text(
                        modifier = Modifier
                            .offset(3.dp, 0.dp),
                        text = "Top Rated Books",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.Default
                        )
                    )

                    LazyRow {
                        items (books.filter { book ->
                            book.category == "Medicine"
                        }){book ->
                            Column {
                                Card (
                                    modifier = Modifier
                                        .clickable {
                                            onBookClick(book.id)
                                        }
                                        .padding(7.dp)
                                        .shadow(elevation = 4.dp)
                                        .border(
                                            width = 1.5.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .size(105.dp,125.dp)
                                ){

                                    Column (
                                        modifier = Modifier.padding(1.dp)
                                    ){
                                        AsyncImage(
                                            model = book.imageUrl,
                                            contentDescription = book.title,
                                            modifier = Modifier
                                                .size(900.dp)
                                                .shadow(elevation = 4.dp)
                                        )
                                    }
                                }

                                Row {
                                    Spacer(
                                        modifier = Modifier.width(10.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .border(0.2.dp, Color.White)
                                            .background(Color.White.copy(alpha = 0.5f))
                                    ){
                                        Text(
                                            text = book.title,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(horizontal = 5.dp)
                                                .fillMaxWidth(),
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis, // for dot dot dot
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight(500),
                                                fontFamily = FontFamily.Default
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    // Course Related Books
                    Text(
                        modifier = Modifier
                            .offset(3.dp, 0.dp),
                        text = "Course Related Books",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.Default
                        )
                    )

                    LazyRow {
                        items (books.filter {book ->
                            when (department){
                                "College of Allied Health and Sciences (CAHS)" -> book.category == "Medicine" || book.category == "Psychology" || book.category == "Anatomy"
                                "College of Criminal Justice Education (CCJE)" -> book.category == "Crime" || book.category == "Law" || book.category == "Murder"
                                "College of Engineering and Architecture (CEA)" -> book.category == "Architecture" || book.category == "Physics" || book.category == "Engineering"
                                "College of Education and Liberal Arts (CELA)" -> book.category == "Politics" || book.category == "Dictionary" || book.category == "Education"
                                "College of Information Technology Education (CITE)" -> book.category == "Technology" || book.category == "Databases" || book.category == "Internet"
                                "College of Management and Accountancy (CMA)" -> book.category == "Entrepreneurship" || book.category == "Business" || book.category == "Management"
                                else -> {
                                    false
                                }
                            }
                        }){book ->
                            Column {
                                Card (
                                    modifier = Modifier
                                        .clickable {
                                            onBookClick(book.id)
                                        }
                                        .padding(7.dp)
                                        .shadow(elevation = 4.dp)
                                        .border(
                                            width = 1.5.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .size(105.dp,125.dp)
                                ){

                                    Column (
                                        modifier = Modifier.padding(1.dp)
                                    ){
                                        AsyncImage(
                                            model = book.imageUrl,
                                            contentDescription = book.title,
                                            modifier = Modifier
                                                .size(900.dp)
                                                .shadow(elevation = 4.dp)
                                        )
                                    }
                                }

                                Row {
                                    Spacer(
                                        modifier = Modifier.width(10.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .border(0.2.dp, Color.White)
                                            .background(Color.White.copy(alpha = 0.5f))
                                    ){
                                        Text(
                                            text = book.title,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(horizontal = 5.dp)
                                                .fillMaxWidth(),
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis, // for dot dot dot
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight(500),
                                                fontFamily = FontFamily.Default
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    // Books You Might Like
                    Text(
                        modifier = Modifier
                            .offset(3.dp, 0.dp),
                        text = "Books You Might Like",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.Default
                        )
                    )

                    LazyRow {
                        items (books.filter {book ->
                            book.category == "Psychology" || book.category == "Business" || book.category == "Dictionary"
                        }){book ->
                            Column {
                                Card (
                                    modifier = Modifier
                                        .clickable {
                                            onBookClick(book.id)
                                        }
                                        .padding(7.dp)
                                        .shadow(elevation = 4.dp)
                                        .border(
                                            width = 1.5.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .size(105.dp,125.dp)
                                ){

                                    Column (
                                        modifier = Modifier.padding(1.dp)
                                    ){
                                        AsyncImage(
                                            model = book.imageUrl,
                                            contentDescription = book.title,
                                            modifier = Modifier
                                                .size(900.dp)
                                                .shadow(elevation = 4.dp)
                                        )
                                    }
                                }

                                Row {
                                    Spacer(
                                        modifier = Modifier.width(10.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .border(0.2.dp, Color.White)
                                            .background(Color.White.copy(alpha = 0.5f))
                                    ){
                                        Text(
                                            text = book.title,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(horizontal = 5.dp)
                                                .fillMaxWidth(),
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis, // for dot dot dot
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight(500),
                                                fontFamily = FontFamily.Default
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    // Top Rated Books
                    Text(
                        modifier = Modifier
                            .offset(3.dp, 0.dp),
                        text = "Off Topic Books",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.Default
                        )
                    )

                    LazyRow {
                        items (books.filter {book ->
                            book.category == "Action" || book.category == "Fiction" || book.category == "Biographies"
                        }){book ->
                            Column {
                                Card (
                                    modifier = Modifier
                                        .clickable {
                                            onBookClick(book.id)
                                        }
                                        .padding(7.dp)
                                        .shadow(elevation = 4.dp)
                                        .border(
                                            width = 1.5.dp,
                                            color = Color.Black,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .size(105.dp,125.dp)
                                ){

                                    Column (
                                        modifier = Modifier.padding(1.dp)
                                    ){
                                        AsyncImage(
                                            model = book.imageUrl,
                                            contentDescription = book.title,
                                            modifier = Modifier
                                                .size(900.dp)
                                                .shadow(elevation = 4.dp)
                                        )
                                    }
                                }

                                Row {
                                    Spacer(
                                        modifier = Modifier.width(10.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .width(100.dp)
                                            .border(0.2.dp, Color.White)
                                            .background(Color.White.copy(alpha = 0.5f))
                                    ){
                                        Text(
                                            text = book.title,
                                            modifier = Modifier
                                                .align(Alignment.Center)
                                                .padding(horizontal = 5.dp)
                                                .fillMaxWidth(),
                                            maxLines = 3,
                                            overflow = TextOverflow.Ellipsis, // for dot dot dot
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight(500),
                                                fontFamily = FontFamily.Default
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                }
            }
        }
    }
}


interface ApiService {
    @GET(HOME_PAGE_URL_PATH)
    fun getFirstName(@Query("student_id") studentId: String): Call<UserDetails>
}

data class UserDetails(
    val firstName: String,
    val department: String,
    val status: Int
)

object RetrofitAccountName {

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
