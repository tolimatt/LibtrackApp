package com.example.libtrack.pagesMain

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.libtrack.backend.SERVER_IP
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


@Composable
fun BooksPage() {

    val scope = rememberCoroutineScope()
    var books by remember { mutableStateOf(listOf<Book>()) }

    LaunchedEffect(true) {
        scope.launch {
            try {
                books = RetrofitBooks.apiService.getBooks()
            } catch (e: Exception) {
                // Handle error (e.g., show a message)
                println("Error fetching books: ${e.message}")
            }
        }
    }


    Column {

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "SEARCHBAR HERE"
        )
        LazyColumn() {

            items(books) { book ->

                Card (
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(7.dp)
                        .shadow(elevation = 4.dp)
                        .border(
                            width = 1.5.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .fillMaxWidth()
                ){
                    Row (
                        modifier = Modifier.padding(15.dp)
                    ){
                        AsyncImage(
                            model = book.image_url,
                            contentDescription = book.title,
                            modifier = Modifier.size(100.dp)
                        )

                        Column (
                            modifier = Modifier.padding(8.dp)
                        ){
                            Text(
                                book.title
                            )
                            Text(
                                "By "+book.author
                            )
                        }

                    }
                }
            }
        }
    }
}

object RetrofitBooks {
    val apiService: ApiServiceBooks by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceBooks::class.java)
    }
}

interface ApiServiceBooks {
    @GET("hello/images.php")
    suspend fun getBooks(): List<Book>

    @GET("hello/books.php")
    suspend fun getBookDetails(@Query("id") id: Int): BookDetails
}

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val image_url: String
)

data class BookDetails(
    val id: Int,
    val title: String,
    val author: String,
    val image_url: String,
    val description: String,
    val pdf_url: String?
)