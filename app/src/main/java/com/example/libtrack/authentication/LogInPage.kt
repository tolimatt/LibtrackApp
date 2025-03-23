package com.example.libtrack.authentication

import android.app.Application
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.libtrack.backend.LoginViewModel
import com.example.libtrack.errorHandling.errorImage
import com.example.libtrack.errorHandling.logoImage
import com.example.libtrack.errorHandling.passwordVisibilityFalseImage
import com.example.libtrack.errorHandling.passwordVisibilityTrueImage

@Composable
fun LogIn(navController: NavHostController) {
    // Context and View Model
    val context = LocalContext.current
    val loginViewModel = remember { LoginViewModel(application = context.applicationContext as Application) } // Initialize ViewModel here

    // For text fields / Text State
    var studentIdTS by remember { mutableStateOf("") }
    var passwordTS by remember { mutableStateOf("") }

    // For error handling / Booleans
    var isStudentId by remember { mutableStateOf(true) } // if null
    var isPassword by remember { mutableStateOf(true) } // if null
    var isRegistered by remember { mutableStateOf(true) } // no db

    // For text field focus
    val studentIdFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    // Password
    var isPasswordVisible by remember { mutableStateOf(false) }

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

                Spacer(
                    modifier = Modifier
                        .height(35.dp)
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
                        fontSize = 17.sp
                    ),
                    placeholder = {
                        Text(
                            text = " Enter Password",
                            fontWeight = FontWeight(400),
                            color = Color.LightGray
                        )
                    },

                    singleLine = true,
                    visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                painter = painterResource(id = if (isPasswordVisible) passwordVisibilityTrueImage else passwordVisibilityFalseImage),
                                contentDescription = "Password Visibility Toggle"
                            )
                        }
                    },
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

                Spacer(
                    modifier = Modifier
                        .height(45.dp)
                )

                Row{
                    if (!isPassword || !isRegistered ){
                        Image(
                            painter = painterResource(id = errorImage),
                            contentDescription = "Error Icon",
                            modifier = Modifier
                                .size(19.dp)
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
                            .align(Alignment.CenterVertically),
                        text =
                        if (!isStudentId && !isPassword){
                            "Student ID and Password is required."
                        } else if(!isStudentId){
                            "Student ID is Required."
                        } else if (!isPassword){
                            "Password is Required."
                        }else if (!isRegistered ){
                            "Incorrect Student ID or Password."
                        } else {
                            ""
                        },

                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 16.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.Default,
                        ),
                    )
                }

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                // ------------------------------------------------------------ LOGIN BUTTON ------------------------------------------------------------
                Button(
                    onClick = {
                        if (studentIdTS.isEmpty()){
                            isStudentId = false
                            isPassword = true
                            isRegistered = true
                            if (passwordTS.isEmpty()){
                                isPassword = false
                            }
                        } else if (passwordTS.isEmpty()){
                            isPassword = false
                            isStudentId = true
                            isRegistered = true
                        } else {
                            isRegistered = true
                            isPassword = true
                            isStudentId = true
                            loginViewModel.loginUser(
                                studentIdTS, // As studentID
                                passwordTS,
                                navController
                            )
                        }
                    },
                    modifier = Modifier
                        .size(width = 290.dp, height = 43.dp)
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(10.dp)
                        ),
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
                                navController.navigate("sign_up_page1")
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
                            navController.navigate("forgot_password_page1")
                        },
                    text = "Forgot Password?",
                    style = TextStyle(
                        color = Color(0xFF006400),
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily.Default
                    )
                )

                val errorMessage by loginViewModel.getErrorMessage().collectAsState(initial = null)

                errorMessage?.let {
                    if (it == "account_not_found"){
                        isRegistered = false
                        isPassword = true
                        isStudentId = true
                    } else {
                        isRegistered = false
                        isPassword = true
                        isStudentId = true
                    }
                }
            }
        }
    }
}
