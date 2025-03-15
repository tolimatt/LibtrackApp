package com.example.libtrack.pagesMain

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.libtrack.errorHandling.aboutUsImage
import com.example.libtrack.errorHandling.changePasswordImage
import com.example.libtrack.errorHandling.historyImage
import com.example.libtrack.errorHandling.personalInfoImage
import com.example.libtrack.errorHandling.servicesImage
import com.example.libtrack.errorHandling.staffImage
import com.example.libtrack.navFunctions.Pages

@Composable
fun SettingsPage(
    studentNumber: String,
    navController: NavHostController
){

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {

        Spacer(
            modifier = Modifier
                .height(5.dp)
        )

        Card(
            modifier = Modifier
                .clickable {

                }
                .padding(4.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ){

            Row (
                modifier = Modifier
                    .padding(15.dp)
            ){

                Image(
                    painter = painterResource(id = personalInfoImage),
                    contentDescription = "Personal Information",
                    modifier = Modifier
                        .size(25.dp)
                )

                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )

                Text(
                    text = "Personal Information",
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.Default
                    )

                )
            }
        }

        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        Card(
            modifier = Modifier
                .clickable {
                    navController.navigate(Pages.Forgot_Password_Page1)
                }
                .padding(4.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ){

            Row (
                modifier = Modifier
                    .padding(15.dp)
            ){

                Image(
                    painter = painterResource(id = changePasswordImage),
                    contentDescription = "Change Password",
                    modifier = Modifier
                        .size(25.dp)
                )

                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )

                Text(
                    text = "Change Password",
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.Default
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        Card(
            modifier = Modifier
                .clickable {

                }
                .padding(4.dp)
                .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
        ){

            Row (
                modifier = Modifier
                    .padding(15.dp)
            ){

                Image(
                    painter = painterResource(id = historyImage),
                    contentDescription = "History",
                    modifier = Modifier
                        .size(25.dp)
                )

                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )

                Text(
                    text = "History",
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.Default
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        Card(
            modifier = Modifier
                .clickable {

                }
                .padding(4.dp)
                .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
        ){

            Row (
                modifier = Modifier
                    .padding(15.dp)
            ){

                Image(
                    painter = painterResource(id = staffImage),
                    contentDescription = "Library Staff",
                    modifier = Modifier
                        .size(25.dp)
                )

                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )

                Text(
                    text = "Library Staff",
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.Default
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        Card(
            modifier = Modifier
                .clickable {

                }
                .padding(4.dp)
                .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
        ){

            Row (
                modifier = Modifier
                    .padding(15.dp)
            ){

                Image(
                    painter = painterResource(id = servicesImage),
                    contentDescription = "Services",
                    modifier = Modifier
                        .size(25.dp)
                )

                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )

                Text(
                    text = "Services",
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600),
                        fontFamily = FontFamily.Default
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        Card(
            modifier = Modifier
                .clickable {

                }
                .padding(4.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ){

            Row (
                modifier = Modifier
                    .padding(15.dp)
            ){

                Image(
                    painter = painterResource(id = aboutUsImage),
                    contentDescription = "About Us",
                    modifier = Modifier
                        .size(25.dp)
                )

                Spacer(
                    modifier = Modifier
                        .width(20.dp)
                )

                Text(
                    text = "About Us",
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
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
