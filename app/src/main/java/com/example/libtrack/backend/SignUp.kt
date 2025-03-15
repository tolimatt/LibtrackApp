package com.example.libtrack.backend

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException


class SignupViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private var signupStatus = MutableStateFlow("") // Status in ViewModel
    private var errorMessage = MutableStateFlow<String?>(null)

    fun signupUser(
        firstName: String,
        lastName: String,
        studentId: String,
        password: String,
        yearLevel: String,
        program: String,
        schoolEmail: String,
        contactNumber: String,
        department: String,
        navController: NavHostController
    ) {
        val userData = UserData(
            firstName,
            lastName,
            studentId,
            password,
            yearLevel,
            program,
            schoolEmail,
            contactNumber,
            department)

        val json = Gson().toJson(userData)
        Log.d("Request Body", json)

        viewModelScope.launch {
            try {
                val response = RetrofitSignup.api.signup(userData)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val status = apiResponse?.status ?: "Unknown status"

                    signupStatus.value = status

                    if (status == "success"){
                        navController.navigate("sign_up_complete"){
                            popUpTo("sign_up_page2") {
                                inclusive = true
                            }
                            popUpTo("sign_up_page1") {
                                inclusive = true
                            }
                        }
                        errorMessage.value = null
                    }

                    signupStatus.value = "Connected: $status" // Update status in ViewModel
                    Log.d("Server Response", status)
                } else {
                    // Handle error responses
                    if (response.code() == 409) { // Check for 409 Conflict

                        errorMessage.value = "Student ID already exists. Please log in."

                        withContext(Dispatchers.Main) {
                            navController.navigate("sign_up_error") {
                                popUpTo("sign_up_page2") {
                                    inclusive = true
                                }
                                popUpTo("sign_up_page1") {
                                    inclusive = true
                                }
                            }
                        }

                    } else {
                        Log.e("Server Error", "Code: ${response.code()}, Message: ${response.message()}")
                    }
                    Log.e("Server Error", "Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: IOException) {
                signupStatus.value = "Network Error: ${e.message}" // Update status
                Log.e("Network Error", e.message.toString())
            } catch (e: Exception) {
                signupStatus.value = "Request failed: ${e.message}" // Update status
                Log.e("Request Errors", e.message.toString())
            }
        }
    }
    fun getErrorMessage(): StateFlow<String?> = errorMessage.asStateFlow()
}

data class ApiResponseSignup(
    val status: String
)

data class UserData(
    val firstName: String,
    val lastName: String,
    val studentId: String,
    val password: String,
    val yearLevel: String,
    val program: String,
    val schoolEmail: String,
    val contactNumber: String,
    val department: String
)

interface SignupServer {
    @POST(SIGN_UP_URL_PATH)
    suspend fun signup(@Body userData: UserData): Response<ApiResponseSignup>
}

object RetrofitSignup {
    val api: SignupServer by lazy {
        val gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        retrofit.create(SignupServer::class.java)
    }
}