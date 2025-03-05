package com.example.libtrack.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.libtrack.navFunctions.Pages
import com.example.libtrack.backend.SERVER_IP
import com.example.libtrack.errorHandling.errorImage
import com.example.libtrack.errorHandling.logoImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow



@Composable
fun LogIn(navController: NavHostController) {
    // Context and View Model
    val context = LocalContext.current
    val loginViewModel = remember { LoginViewModel(context) } // Initialize ViewModel here

    // For text fields / Text State
    var studentIdTS by remember { mutableStateOf("03-2324-032803") }
    var passwordTS by remember { mutableStateOf("Kirsteen12345") }

    // For error handling / Booleans
    var isStudentId by remember { mutableStateOf(true) }
    var isPassword by remember { mutableStateOf(true) }
    var isRegistered by remember { mutableStateOf(true) }

    // For text field focus
    val studentIdFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val studentNumber = studentIdTS

    LaunchedEffect(key1 = true) { // Use LaunchedEffect to collect navigation events
        loginViewModel.navigationEvent.collect { route ->
            navController.navigate(route)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->

        LazyColumn (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ){
            items(1){

                // Logo
                Image(
                    painter = painterResource(id = logoImage),
                    contentDescription = "Logo Loading",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(
                    modifier = Modifier
                        .height(50.dp)
                )

                // Title
                Text(
                    text = "Welcome to LibTrack",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 28.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.Default
                    )
                )

                Spacer(
                    modifier = Modifier
                        .height(15.dp)
                )

                // Subtitle
                Text(
                    text = "UPang LibTrack - Your literary companion",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily.Default,
                    )
                )

                Spacer(
                    modifier = Modifier
                        .height(23.dp)
                )

                Text(
                    modifier = Modifier
                        .offset(
                            (-131).dp, 0.dp),
                    text = "STUDENT ID",
                    fontSize = 12.sp,
                    color = Color(0xFF727D83)
                )

                // ------------------------------------------------------------ STUDENT ID ------------------------------------------------------------
                TextField(
                    modifier = Modifier
                        .border(
                            width = 1.2.dp,
                            color = if(!isStudentId || !isRegistered) Color.Red else Color(0xFF727D83),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .width(350.dp)
                        .focusRequester(studentIdFocusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 17.sp
                    ),
                    placeholder = {
                        Text(
                            text = "Enter Student ID",
                            fontWeight = FontWeight(400),
                            color = Color.LightGray
                        )
                    },

                    singleLine = true,
                    value = studentIdTS,
                    onValueChange = { if (it.length <= 14) studentIdTS = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {passwordFocusRequester.requestFocus()}
                    ),
                )

                Row{
                    if (!isStudentId || !isRegistered){
                        Image(
                            painter = painterResource(id = errorImage),
                            contentDescription = "Error Icon",
                            modifier = Modifier
                                .offset((-88).dp,5.dp)
                                .size(16.dp)
                        )
                    } else {
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp)
                    )

                    Text(
                        modifier = Modifier
                            .offset(
                                (-90).dp, 5.dp)
                            .align(Alignment.CenterVertically),
                        text =
                        if (!isStudentId){
                            "Student ID is required."
                        } else if (!isRegistered){
                            "Invalid Student ID."
                        } else {
                            ""
                        },

                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 13.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.Default,
                        ),
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(18.dp)
                )

                Text(
                    modifier = Modifier
                        .offset(
                            (-133).dp, 0.dp),
                    text = "PASSWORD",
                    fontSize = 12.sp,
                    color = Color(0xFF727D83)
                )

                // ------------------------------------------------------------ PASSWORD ------------------------------------------------------------

                // Hide Keyboard
                val keyboardController = LocalSoftwareKeyboardController.current

                TextField(
                    modifier = Modifier
                        .border(
                            width = 1.2.dp,
                            color = if(!isPassword || !isRegistered) Color.Red else Color(0xFF727D83),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .width(350.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp
                    ),
                    placeholder = {
                        Text(
                            text = " Enter Password",
                            fontWeight = FontWeight(400),
                            color = Color.LightGray
                        )
                    },

                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    value = passwordTS,
                    onValueChange = {passwordTS = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    )
                )

                Row{
                    if (!isPassword || !isRegistered){
                        Image(
                            painter = painterResource(id = errorImage),
                            contentDescription = "Error Icon",
                            modifier = Modifier
                                .offset((-88).dp,5.dp)
                                .size(16.dp)
                        )
                    } else {
                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp)
                    )

                    Text(
                        modifier = Modifier
                            .offset(
                                (-90).dp, 5.dp)
                            .align(Alignment.CenterVertically),
                        text =
                        if (!isStudentId){
                            "Password is required."
                        } else if (!isRegistered){
                            "Invalid Password."
                        } else {
                            ""
                        },

                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 13.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.Default,
                        ),
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(28.dp)
                )

                Row{

                    Text(
                        text = "Don't have an account? ",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            fontFamily = FontFamily.Default,
                        )
                    )

                    Text(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Pages.Sign_Up_Page1)
                            },
                        text = "Register!",
                        style = TextStyle(
                            color = Color(0xFF006400),
                            fontSize = 15.sp,
                            fontWeight = FontWeight(400),
                            fontFamily = FontFamily.Default
                        )
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Pages.Forgot_Password_Page1)
                        },
                    text = "Forgot Password?",
                    style = TextStyle(
                        color = Color(0xFF006400),
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily.Default
                    )
                )

                Spacer(
                    modifier = Modifier
                        .height(60.dp)
                )

                // ------------------------------------------------------------ LOGIN BUTTON ------------------------------------------------------------
                Button(
                    onClick = {
                        if (studentIdTS == ""){
                            isStudentId = false
                            isPassword = true
                            isRegistered = true
                            if (passwordTS == ""){
                                isPassword = false
                            }
                        } else if (passwordTS == ""){
                            isPassword = false
                            isStudentId = true
                            isRegistered = true
                        } else {
                            isRegistered = true
                            isPassword = true
                            isStudentId = true
                            //loginViewModel.loginUser(studentIdTS, passwordTS)
                            navController.navigate("MainPage/$studentNumber")

                        }
                    },
                    modifier = Modifier
                        .size(width = 290.dp, height = 43.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF72AF7B),
                        contentColor = Color.White
                    )
                ) {

                    Text(
                        text = "Login",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(600),
                            fontFamily = FontFamily.Default
                        )
                    )
                }
            }
        }
    }
}


class LoginViewModel(@SuppressLint("StaticFieldLeak") private var context: Context) : androidx.lifecycle.ViewModel() {

    private var loginStatus = MutableStateFlow("")

    private val _navigationEvent = MutableSharedFlow<String>() // Use SharedFlow
    val navigationEvent = _navigationEvent.asSharedFlow()


    fun loginUser(studentId: String, password: String) {
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

                    if (status == "success") {
                        Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                        _navigationEvent.emit(Pages.Main_Page)
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
}


data class ApiResponseLogin(
    val status: String,
    val message: String? = null
)

data class LoginData(
    val studentId: String,
    val password: String
)

interface LoginServer {
    @POST("libTrack/login.php")
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