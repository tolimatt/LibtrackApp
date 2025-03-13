package com.example.libtrack.backend

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.libtrack.pagesMain.ApiResponseBorrow
import com.example.libtrack.pagesMain.BorrowData
import com.example.libtrack.pagesMain.RetrofitBorrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                        else -> {
                            Toast.makeText(context,"Error", Toast.LENGTH_SHORT).show()

                        }
                    }
                    _borrowStatus.value = apiResponse?.status ?: "Unknown status"
                } else {
                    _errorMessage.value = response.body()?.error ?: "Unknown error"
                }
            }
            override fun onFailure(call: Call<ApiResponseBorrow>, t: Throwable) {
                _errorMessage.value = t.message
            }
        })
    }
    fun getErrorMessage(): StateFlow<String?> = _errorMessage
}