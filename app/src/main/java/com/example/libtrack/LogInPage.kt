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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogIn(navController: NavHostController) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ){
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

        Text(
            text = "Welcome to LibTrack",
            style = TextStyle(
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight(700),
                fontFamily = FontFamily.Default,

            )
        )

        Spacer(
            modifier = Modifier
                .height(15.dp)
        )

        Text(
            text = "PHINMA UPang - Your literary companion",
            style = TextStyle(
                color = Color.Gray,
                fontSize = 17.sp,
                fontWeight = FontWeight(400),
                fontFamily = FontFamily.Default,

            )
        )

        Spacer(
            modifier = Modifier
                .height(15.dp)
        )

        // Email
        val emailTS = remember { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .border(
                width = 2.dp,
                color = Color(0xFFC1C1C1),
                shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = emailTS.value,
            onValueChange = { if (it.length <= 14) emailTS.value = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
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

        Spacer(
            modifier = Modifier
                .height(15.dp)
        )

        // Password
        val passwordTS = remember { mutableStateOf("") }
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = passwordTS.value,
            onValueChange = {passwordTS.value = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
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

        Spacer(
            modifier = Modifier
                .height(20.dp)
        )

        Row (){
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

        Button(
            onClick = {
                if (emailTS.value == "00-0000-000000" && passwordTS.value == "0"){
                    navController.navigate(Pages.Home_Page)
                } else {
                    navController.navigate(Pages.Log_In)
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
