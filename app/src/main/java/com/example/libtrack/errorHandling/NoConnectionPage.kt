package com.example.libtrack.errorHandling

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun NoConnectionPage(navHostController: NavHostController) {

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = errorImage),
                contentDescription = "Error",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            Text(
                text = "No Connection to Server",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Text(
                text = "Connection to the Server can't be establish. Please check your Server Connection",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                )
            )

            Spacer(
                modifier = Modifier
                    .height(80.dp)
            )

            Button(
                onClick = {
                    navHostController.navigate("SplashScreenPage")
                },
                modifier = Modifier
                    .size(width = 290.dp, height = 43.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF72AF7B),
                    contentColor = Color.White
                )
            ){
                Text(
                    text = "Try Again",
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

@Composable
fun AccountErrorPage(navHostController: NavHostController) {

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = errorImage),
                contentDescription = "Error",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(70.dp)
            )

            Spacer(
                modifier = Modifier
                    .height(30.dp)
            )

            Text(
                text = "No Connection to Server",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Text(
                text = "Connection to the Server can't be establish. Please check your Server Connection",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                )
            )

            Spacer(
                modifier = Modifier
                    .height(80.dp)
            )

            Button(
                onClick = {
                    navHostController.navigate("SplashScreenPage")
                },
                modifier = Modifier
                    .size(width = 290.dp, height = 43.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF72AF7B),
                    contentColor = Color.White
                )
            ){
                Text(
                    text = "Try Again",
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
