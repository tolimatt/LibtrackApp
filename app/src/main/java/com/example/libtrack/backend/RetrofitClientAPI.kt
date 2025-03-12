package com.example.libtrack.backend

import com.example.libtrack.pagesMain.Book
import com.example.libtrack.pagesMain.BookDetails
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val SERVER_IP = "http://192.168.1.59/"

// Also change -> res/xml/server_address.xml to the same SERVER_IP

// Sign Up
const val SIGN_UP_URL_PATH = "libTrack/signup.php"

// Log in
const val LOG_IN_URL_PATH = "libTrack/login.php"

// Home Page
const val HOME_PAGE_URL_PATH = "hello/tests.php"

// Book Images
const val BOOK_IMAGES_URL_PATH = "hello/images.php"

// Book List
const val BOOK_LIST_URL_PATH = "hello/books.php"


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