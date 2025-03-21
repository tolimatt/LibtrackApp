package com.example.libtrack.backend

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


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
    @GET(BOOK_IMAGES_URL_PATH)
    suspend fun getBooks(): List<Book>

    @GET(BOOK_LIST_URL_PATH)
    suspend fun getBookDetails(@Query("id") id: Int): BookDetails
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