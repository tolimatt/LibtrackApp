package com.example.libtrack.backend

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

class BorrowViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context: Context = application.applicationContext
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _borrowStatus = MutableStateFlow<String?>(null)
    
    private val apiService = RetrofitBorrow.apiService

    // Function to make the borrow request
    fun borrowBook(borrowRequest: BorrowData) {
        _errorMessage.value = null
        _borrowStatus.value = null

        // Make network request asynchronously
        apiService.insertBorrowData(borrowRequest).enqueue(object : Callback<ApiResponseBorrow> {
            override fun onResponse(call: Call<ApiResponseBorrow>, response: Response<ApiResponseBorrow>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val status = apiResponse?.status ?: "Unknown status"

                    _borrowStatus.value = status

                    when (status){
                        "success" -> { // Book Has Been Borrowed Successfully
                            Toast.makeText(context, "Book Borrowed Successfully", Toast.LENGTH_SHORT).show()
                            _borrowStatus.value = "success"
                        }
                        "already_borrowed" -> { // Book Has Been Borrowed Already
                            Toast.makeText(context,"You Already Borrowed This Book", Toast.LENGTH_SHORT).show()
                            _borrowStatus.value = "already_borrowed"
                        }
                        "limit" -> { // Cannot Borrow Book Since You Have Reached Your Borrow Limit
                            Toast.makeText(context,"You can only borrow 3 books at a time", Toast.LENGTH_SHORT).show()
                            _borrowStatus.value = "limit"
                        }
                        "returned" -> { // Book Not Found
                            Toast.makeText(context,"OK", Toast.LENGTH_SHORT).show()
                            _borrowStatus.value = "not_found"
                        }
                        else -> {
                            Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()
                            _borrowStatus.value = "error"
                        }
                    }
                } else {
                    _errorMessage.value = response.body()?.error ?: "Unknown error"
                }
            }
            override fun onFailure(call: Call<ApiResponseBorrow>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }

    fun checkBorrowStatus(studentId: String, bookCode: String) {
        _errorMessage.value = null
        _borrowStatus.value = "loading"

        apiService.checkIfBorrowed(studentId, bookCode).enqueue(object : Callback<ApiResponseBorrow> {
            override fun onResponse(call: Call<ApiResponseBorrow>, response: Response<ApiResponseBorrow>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    _borrowStatus.value = apiResponse?.status ?: "not_borrowed"
                } else {
                    _borrowStatus.value = "error"
                }
            }

            override fun onFailure(call: Call<ApiResponseBorrow>, t: Throwable) {
                _borrowStatus.value = "error"
            }
        })
    }
    fun getBorrowStatus(): StateFlow<String?> = _borrowStatus
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

    @GET(STATUS_BORROW_URL_PATH) // Adjust based on your API endpoint
    fun checkIfBorrowed(
        @Query("studentId") studentId: String,
        @Query("bookCode") bookCode: String
    ): Call<ApiResponseBorrow>

}

object RetrofitBorrow {
    val apiService: BorrowApiService by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Add the OkHttpClient
            .build()
            .create(BorrowApiService::class.java)
    }
}

