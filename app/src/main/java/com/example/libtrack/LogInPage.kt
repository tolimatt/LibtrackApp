package com.example.libtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(navController: NavHostController) {

    // For text fields / Text State
    val studentIdTS = remember { mutableStateOf("") }
    val libCardTS = remember { mutableStateOf("") }

    // For error handling / Booleans
    var isStudentId by remember { mutableStateOf(true) }
    var isLibCard by remember { mutableStateOf(true) }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ){
        // Logo
        Image(
            painter = painterResource(id = R.drawable.load),
            contentDescription = "Logo Loading",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(
            modifier = Modifier
                .height(40.dp)
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

        // Student ID
        TextField(
            modifier = Modifier
                .border(
                width = 2.dp,
                color = if(!isStudentId) Color.Red else Color(0xFFC1C1C1),
                shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = studentIdTS.value,
            onValueChange = { if (it.length <= 14) studentIdTS.value = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (studentIdTS.value == "00-0000-000000" && libCardTS.value == "0"){
                        isStudentId = true
                        isLibCard = true
                        navController.navigate(Pages.Home_Page)
                    } else if (studentIdTS.value == ""){
                        isStudentId = false
                        isLibCard = true
                        if (libCardTS.value == ""){
                            isLibCard = false
                        }
                    } else if (libCardTS.value == ""){
                        isLibCard = false
                        isStudentId = true
                    }else {
                        isLibCard = true
                        isStudentId = true
                        navController.navigate(Pages.Splash_Screen)
                    }
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Enter Student ID",
                    fontWeight = FontWeight(600))
            },
            singleLine = true
        )

        if (!isStudentId){
            Text(
                text ="Student ID is required.",
                modifier = Modifier
                    .offset(
                        (-60).dp, 5.dp),
                style = TextStyle(
                    color = Color.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily.Default,
                    ),
            )
        }

        Spacer(
            modifier = Modifier
                .height(if (!isStudentId) 17.dp else 22.dp)
        )

        // Library Card
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = if(!isLibCard) Color.Red else Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = libCardTS.value,
            onValueChange = {libCardTS.value = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (studentIdTS.value == "00-0000-000000" && libCardTS.value == "0"){
                        isStudentId = true
                        isLibCard = true
                        navController.navigate(Pages.Home_Page)
                    } else if (studentIdTS.value == ""){
                        isStudentId = false
                        isLibCard = true
                        if (libCardTS.value == ""){
                            isLibCard = false
                        }
                    } else if (libCardTS.value == ""){
                        isLibCard = false
                        isStudentId = true
                    }else {
                        isLibCard = true
                        isStudentId = true
                        navController.navigate(Pages.Splash_Screen)
                    }
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Library Card No.",
                    fontWeight = FontWeight(600))
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        if (!isLibCard){
            Text(
                text ="Library number is required.",
                modifier = Modifier
                    .offset(
                        (-45).dp, 5.dp),
                style = TextStyle(
                    color = Color.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily.Default,
                ),
            )
        }

        Spacer(
            modifier = Modifier
                .height(if (!isLibCard) 17.dp else 22.dp)
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
                    navController.navigate(Pages.Sign_Up)
                    },
                text = "Sign Up",
                style = TextStyle(
                    color = Color(0xFF006400),
                    fontSize = 15.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline
                )
            )
        }

        Spacer(
            modifier = Modifier
                .height(180.dp)
        )

        // Login Button
        Button(
            onClick = {
                if (studentIdTS.value == "00-0000-000000" && libCardTS.value == "0"){
                    isStudentId = true
                    isLibCard = true
                    navController.navigate(Pages.Home_Page)
                } else if (studentIdTS.value == ""){
                    isStudentId = false
                    isLibCard = true
                    if (libCardTS.value == ""){
                        isLibCard = false
                    }
                } else if (libCardTS.value == ""){
                    isLibCard = false
                    isStudentId = true
                }else {
                    isLibCard = true
                    isStudentId = true
                    navController.navigate(Pages.Splash_Screen)
                }
            },
            modifier = Modifier
                .size(width = 280.dp, height = 43.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.Default
                )
            )
        }
    }
}
