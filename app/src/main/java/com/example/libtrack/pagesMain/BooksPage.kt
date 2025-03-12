package com.example.libtrack.pagesMain

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.libtrack.backend.BOOK_IMAGES_URL_PATH
import com.example.libtrack.backend.BOOK_LIST_URL_PATH
import com.example.libtrack.backend.RetrofitBooks
import com.example.libtrack.backend.SERVER_IP
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


@Composable
fun BooksPage(
    studentNumber: String,
    onBookClick: (Any) -> Unit
) {

    val scope = rememberCoroutineScope()
    var books by remember { mutableStateOf(listOf<Book>()) }
    var coolTS by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) } // State for loading

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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            modifier = Modifier
                .padding(5.dp)
                .border(
                    width = 1.2.dp,
                    color = Color(0xFF727D83),
                    shape = RoundedCornerShape(2.dp)
                )
                .fillMaxWidth(),
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
                    text = " Search Books",
                    fontWeight = FontWeight(400),
                    color = Color.LightGray
                )
            },
            singleLine = true,
            value = coolTS,
            onValueChange = { coolTS = it },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
        )

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
            LazyColumn(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                items(books) { book ->
                    Card(
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
                            .fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(10.dp)) {
                            AsyncImage(
                                model = book.imageUrl,
                                contentDescription = book.title,
                                modifier = Modifier.size(100.dp)
                            )

                            Column(modifier = Modifier.padding(8.dp)) {
                                Spacer(modifier = Modifier.height(3.dp))

                                Text(
                                    text = book.title,
                                    fontWeight = FontWeight(600),
                                    fontSize = 17.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "By " + book.author,
                                    style = TextStyle(
                                        color = Color.Gray,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// Book Images
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val imageUrl: String,
    val category: String
)

// Book List
data class BookDetails(
    val id: Int,
    val title: String,
    val author: String,
    val bookNumber: String,
    val description: String,
    val totalCopies: Int,
    val availableCopies: Int,
    val imageUrl: String,
    val pdfUrl: String?,
    val category: String
)