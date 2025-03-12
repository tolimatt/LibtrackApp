package com.example.libtrack.backend

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.libtrack.navFunctions.Pages
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    @SuppressLint("StaticFieldLeak")
    private val context: Context = application.applicationContext
    private var loginStatus = MutableStateFlow("")
    private var errorMessage = MutableStateFlow<String?>(null)

    fun loginUser(studentId: String, password: String, studentNumber: String, navController: NavHostController) { // Add studentNumber and navController

        val loginData = LoginData(studentId, password)
        val json = Gson().toJson(loginData)
        Log.d("Request Body", json)

        viewModelScope.launch {
            try {
                val response = RetrofitLogin.api.login(loginData)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val status = apiResponse?.status ?: "Unknown status"

                    loginStatus.value = status

                    when (status){
                        "success" -> {
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            navController.navigate("MainPage/$studentNumber"){
                                popUpTo(Pages.Log_In) {
                                    inclusive = true
                                }
                            }
                            errorMessage.value = null
                        }
                        "account_not_found" -> {
                            errorMessage.value = "Account not found. Please check your credentials."
                            Toast.makeText(context, "Account not found", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            errorMessage.value = "Login failed. Please try again."
                            Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
                        }
                    }
                    Log.d("Server Response", status)
                } else {
                    loginStatus.value = "Error: ${response.code()} - ${response.message()}"
                    Log.e("Server Error", "Code: ${response.code()}, Message: ${response.message()}")
                    Toast.makeText(context, "Invalid Account", Toast.LENGTH_LONG).show()
                }
            } catch (e: IOException) {
                loginStatus.value = "Network Error: ${e.message}"
                Log.e("Network Error", e.message.toString())
            } catch (e: Exception) {
                loginStatus.value = "Request failed: ${e.message}"
                Log.e("Request Error", e.message.toString())
            }
        }
    }
    fun getErrorMessage(): StateFlow<String?> = errorMessage.asStateFlow()
}

data class ApiResponseLogin(
    val status: String,
    val message: String? = null
)

data class LoginData(
    val studentId: String,
    val password: String
)
//
interface LoginServer {
    @POST(LOG_IN_URL_PATH)
    suspend fun login(@Body loginData: LoginData): Response<ApiResponseLogin>
}

object RetrofitLogin {
    val api: LoginServer by lazy {
        val gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        retrofit.create(LoginServer::class.java)
    }
}