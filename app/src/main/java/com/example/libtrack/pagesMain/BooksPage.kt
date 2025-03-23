package com.example.libtrack.pagesMain

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.libtrack.backend.Book
import com.example.libtrack.backend.RetrofitAccountName
import com.example.libtrack.backend.RetrofitBooks
import com.example.libtrack.backend.UserDetails
import com.example.libtrack.errorHandling.errorImage
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun BooksPage(
    studentID: String,
    navController: NavHostController,
    onBookClick: (Any) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var validAccount by remember { mutableStateOf<String?>("") }

    var books by remember { mutableStateOf(listOf<Book>()) }
    var filteredBooks by remember { mutableStateOf(listOf<Book>()) }
    var searchBookTS by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) } // State for loading


    // Set of selected filters
    var selectedFilters by remember { mutableStateOf(setOf<String>()) }

    val categories = mapOf(
        "Medicine" to listOf("Medicine"),
        "Psychology" to listOf("Psychology"),
        "Anatomy" to listOf("Anatomy"),
        "Crime" to listOf("Crime"),
        "Law" to listOf("Law"),
        "Murder" to listOf("Murder"),
        "Architecture" to listOf("Architecture"),
        "Physics" to listOf("Physics"),
        "Engineering" to listOf("Engineering"),
        "Politics" to listOf("Politics"),
        "Dictionary" to listOf("Dictionary"),
        "Education" to listOf("Education"),
        "Technology" to listOf("Technology"),
        "Databases" to listOf("Databases"),
        "Internet" to listOf("Internet"),
        "Entrepreneurship" to listOf("Entrepreneurship"),
        "Business" to listOf("Business"),
        "Management" to listOf("Management"),
        "Action" to listOf("Action"),
        "Fiction" to listOf("Fiction"),
        "Biographies" to listOf("Biographies")
    )


    // Update filtered books based on search AND active filters
    filteredBooks = books.filter { book ->
        val matchesSearch = searchBookTS.isEmpty() ||
                book.title.contains(searchBookTS, ignoreCase = true) ||
                book.author.contains(searchBookTS, ignoreCase = true)

        val matchesCategory = selectedFilters.isEmpty() || book.category in selectedFilters

        matchesSearch && matchesCategory
    }


    LaunchedEffect(true) {
        scope.launch {
            try {
                books = RetrofitBooks.apiService.getBooks()
                filteredBooks = books // Initialize filteredBooks with all books
                isLoading = false // Set loading to false when books are fetched

                val response = RetrofitAccountName.apiServiceStudentDetails.getFirstName(studentID)
                response.enqueue(object : Callback<UserDetails> {
                    override fun onResponse(
                        call: Call<UserDetails>,
                        response: Response<UserDetails>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
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
                // Handle error (e.g., show a message)
                println("Error fetching books: ${e.message}")
                isLoading = false // Set loading to false even on error
            }
        }
    }

    if (validAccount == "0"){

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = errorImage),
                contentDescription = "Error",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            Text(
                text = "Account Error",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Text(
                text = "Please Proceed to Librarian's Office to Resolve this Issue",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                )
            )

            Spacer(
                modifier = Modifier
                    .height(80.dp)
            )

            Button(
                onClick = {
                    navController.navigate("splash_screen_page")
                },
                modifier = Modifier
                    .size(width = 290.dp, height = 43.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF72AF7B),
                    contentColor = Color.White
                )
            ){
                Text(
                    text = "Try Again",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.Default
                    )
                )
            }
        }
    } else {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {

            Spacer(
                modifier = Modifier.height(7.dp)
            )

            TextField(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(22.dp)
                    ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontSize = 20.sp
                ),
                placeholder = {
                    Text(
                        text = " Search Books or Authors",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray
                    )
                },
                singleLine = true,
                value = searchBookTS,
                onValueChange = { input ->
                    searchBookTS = input
                    // Filter books based on title or author
                    filteredBooks = if (input.isEmpty()) {
                        books
                    } else {
                        books.filter { book ->
                            book.title.contains(input, ignoreCase = true) ||
                                    book.author.contains(input, ignoreCase = true)
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
            )

            LazyRow {
                items(categories.keys.toList()) { filter ->
                    Button(
                        onClick = {
                            selectedFilters = if (filter in selectedFilters) {
                                selectedFilters - filter
                            } else {
                                selectedFilters + filter
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (filter in selectedFilters) Color(0xFF1E5128) else Color.White.copy(alpha = 0.9f),
                            contentColor = if (filter in selectedFilters) Color.White else Color.Black.copy(alpha = 0.6f)
                        ),
                        modifier = Modifier
                            .padding(4.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(filter)
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                ){
                    items(filteredBooks) { book ->

                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally, // Center everything
                            modifier = Modifier.fillMaxWidth()
                        ){
                            Card (
                                modifier = Modifier
                                    .clickable {
                                        onBookClick(book.id)
                                    }
                                    .padding(7.dp)
                                    .shadow(
                                        elevation = 25.dp,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .size(
                                        width = 130.dp,
                                        height = 160.dp
                                    )
                                    .border(
                                        width = 1.5.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                colors = cardColors(
                                    containerColor = Color.White
                                ),
                                elevation = cardElevation(
                                    defaultElevation = 10.dp
                                ),

                                ){

                                Column (modifier = Modifier
                                    .padding(5.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
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

                            Box(
                                modifier = Modifier
                                    .clickable {
                                        onBookClick(book.id)
                                    }
                                    .width(120.dp)
                                    .background(Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(8.dp))
                                    .padding( horizontal = 1.dp, vertical = 3.dp)
                            ){

                                Column {

                                    Row {

                                        Spacer(
                                            modifier = Modifier.width(10.dp)
                                        )

                                        Text(
                                            text = book.title,
                                            modifier = Modifier
                                                .padding(horizontal = 5.dp)
                                                .fillMaxWidth(),
                                            maxLines = 2,
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

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Row {

                                        Spacer(
                                            modifier = Modifier.width(10.dp)
                                        )

                                        Text(
                                            text = "By "+book.author,
                                            modifier = Modifier
                                                .padding(horizontal = 5.dp)
                                                .fillMaxWidth(),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis, // for dot dot dot
                                            textAlign = TextAlign.Center,
                                            style = TextStyle(
                                                color = Color(0xFF5E6366),
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight(500),
                                                fontFamily = FontFamily.Default
                                            )
                                        )
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
    }
}

