package com.example.libtrack.backend

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(HOME_PAGE_URL_PATH)
    fun getFirstName(@Query("student_id") studentId: String): Call<UserDetails>
}

// Get the Info of the user according to the student ID
data class UserDetails(
    val firstName: String,
    val lastName: String,
    val schoolEmail: String,
    val contactNumber: String,
    val program: String,
    val yearLevel: String,
    val department: String,
    val status: Int
)

object RetrofitAccountName {

    val apiServiceStudentDetails: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
