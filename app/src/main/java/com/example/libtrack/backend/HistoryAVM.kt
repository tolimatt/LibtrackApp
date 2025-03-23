package com.example.libtrack.backend

import android.util.Log
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


data class HistoryItem(
    val bookCode: String,
    val title: String,
    val borrowedDate: String,
    val dueDate: String,
    val returnDate: String,
    val status: String
)

interface YourApiService {
    @GET(BORROWED_BOOKS_URL_PATH)
    suspend fun getStudentHistory(@Query("studentID") studentId: String): Response<List<HistoryItem>>
}

// Retrofit Instance
object RetrofitInstance {
    private const val BASE_URL = SERVER_IP // Replace with your base URL

    val api: YourApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YourApiService::class.java)
    }
}

class HistoryViewModel{
    suspend fun fetchStudentHistory(studentId: String): List<HistoryItem>? {
        return try {
            val response = RetrofitInstance.api.getStudentHistory(studentId) // Use the Retrofit instance
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("StudentHistory", "Error: ${response.code()}, ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("StudentHistory", "Exception: ${e.message}")
            null
        }
    }
}

data class AttendanceItem(
    val entryTime: String,
    val day: String
)

interface AttendanceApiService {
    @GET(ATTENDANCE_CHECKER_URL_PATH) // Change this to your actual endpoint
    suspend fun getStudentAttendance(@Query("studentID") studentId: String): Response<List<AttendanceItem>>
}

object RetrofitAttendanceInstance {
    private const val BASE_URL = SERVER_IP // Replace with actual URL

    val api: AttendanceApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AttendanceApiService::class.java)
    }
}

class AttendanceViewModel {
    suspend fun fetchStudentAttendance(studentId: String): List<AttendanceItem>? {
        return try {
            val response = RetrofitAttendanceInstance.api.getStudentAttendance(studentId)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("AttendanceViewModel", "API Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("AttendanceViewModel", "Exception: ${e.message}")
            null
        }
    }
}