package com.example.libtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun LogIn(navController: NavHostController) {

    // For text fields / Text State
    var studentIdTS by remember { mutableStateOf("") }
    var passwordTS by remember { mutableStateOf("") }

    // For error handling / Booleans
    var isStudentId by remember { mutableStateOf(true) }
    var isPassword by remember { mutableStateOf(true) }
    var isRegistered by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
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
                    .width(350.dp),
                value = studentIdTS,
                onValueChange = { if (it.length <= 14) studentIdTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
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
                        fontWeight = FontWeight(400))
                },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (studentIdTS == "00-0000-000000" && passwordTS == "0"){
                            isStudentId = true
                            isPassword = true
                            navController.navigate(Pages.Home_Page)
                        } else if (studentIdTS == ""){
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
                        }else {
                            isStudentId = true
                            isPassword = true
                            isRegistered = false
                        }
                    }
                ),
            )

            if (!isStudentId){
                Text(
                    text ="Student ID is required.",
                    modifier = Modifier
                        .offset(
                            (-100).dp, 5.dp),
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 13.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily.Default,
                    ),
                )
            }

            if (!isRegistered){
                Text(
                    text ="Invalid Student ID.",
                    modifier = Modifier
                        .offset(
                            (-114).dp, 5.dp),
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
                    .height(if (!isStudentId || !isRegistered) 17.dp else 22.dp)
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
            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isPassword || !isRegistered) Color.Red else Color(0xFF727D83),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .width(350.dp),
                value = passwordTS,
                onValueChange = {passwordTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
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
                        text = "Password",
                        fontWeight = FontWeight(400))
                },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (studentIdTS == "00-0000-000000" && passwordTS == "0"){
                            isStudentId = true
                            isPassword = true
                            navController.navigate(Pages.Home_Page)
                        } else if (studentIdTS == ""){
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
                        }else {
                            isStudentId = true
                            isPassword = true
                            isRegistered = false
                        }
                    }
                ),
            )

            if (!isPassword){
                Text(
                    text ="Password is required.",
                    modifier = Modifier
                        .offset(
                            (-101).dp, 5.dp),
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 13.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily.Default,
                    ),
                )
            }

             if (!isRegistered){
                 Text(
                     text ="Invalid Password.",
                     modifier = Modifier
                         .offset(
                             (-114).dp, 5.dp),
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
                    .height(if (!isPassword || !isRegistered) 22.dp else 27.dp)
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
                    .height(100.dp)
            )

            // ------------------------------------------------------------ LOGIN BUTTON ------------------------------------------------------------
            Button(
                onClick = {
                    if (studentIdTS == "00-0000-000000" && passwordTS == "0"){
                        isStudentId = true
                        isPassword = true
                        navController.navigate(Pages.Home_Page)
                    } else if (studentIdTS == ""){
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
                    }else {
                        isStudentId = true
                        isPassword = true
                        isRegistered = false
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
