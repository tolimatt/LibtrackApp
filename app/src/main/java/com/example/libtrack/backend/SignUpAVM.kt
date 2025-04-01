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
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

// Check and get if the student ID is already on the database
suspend fun checkStudentID(inputText: String): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val encodedText = URLEncoder.encode(inputText, "UTF-8")
            val url = URL(SERVER_IP+CHECK_STUDENT_URL_PATH+"?text=$encodedText")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                // The PHP will return true or false depending if the student ID is already inside the database / is already registered
                return@withContext response.toString().trim().equals("true", ignoreCase = true)
            } else {
                return@withContext false // Return false if not
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }
    }
}


// Check and Get if the school email is already on the database
suspend fun checkEmail(inputText: String): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            val encodedText = URLEncoder.encode(inputText, "UTF-8")
            val url = URL(SERVER_IP+ CHECK_EMAIL_URL_PATH+"?text=$encodedText")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()
                // The PHP will return true or false depending if the student ID is already inside the database / is already registered
                return@withContext response.toString().trim().equals("true", ignoreCase = true)
            } else {
                return@withContext false // Return false if not
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }
    }
}


// Sign Up View Model
class SignupViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private var signupStatus = MutableStateFlow("") // Status in ViewModel
    private var errorMessage = MutableStateFlow<String?>(null)

    fun signupUser(
        firstName: String,
        lastName: String,
        studentID: String,
        phinmaEmail: String,
        password: String,
        program: String,
        yearLevel: String,
        department: String,
        contactNumber: String,
        navController: NavHostController
    ) {
        val userData = UserData(
            firstName,
            lastName,
            studentID,
            phinmaEmail,
            password,
            program,
            yearLevel,
            department,
            contactNumber
        )

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
                        navController.navigate("sign_up_complete_page"){ // Register is complete and successful
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
                    if (response.code() == 409) { // Error Handling Just in case

                        errorMessage.value = "Student ID already exists. Please log in."

                        withContext(Dispatchers.Main) {
                            navController.navigate("sign_up_error_page") {
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
                signupStatus.value = "Request failed: ${e.message}" // Alse Update status
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
    val studentID: String,
    val phinmaEmail: String,
    val password: String,
    val program: String,
    val yearLevel: String,
    val department: String,
    val contactNumber: String
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