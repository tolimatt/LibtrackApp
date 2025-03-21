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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
    studentID: String,
    navController: NavHostController
){

    LazyColumn (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(13.dp)
    ) {
        items(1){

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .background(color = Color.White)
                    .size(500.dp, 205.dp)
            ){
                Column {

                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )

                    Card(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("personal_info_page/$studentID")
                            }
                            .padding(4.dp)
                            .fillMaxWidth()
                        ,
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
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.Default
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 1.dp, bottom = 1.dp)
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
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.Default
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 1.dp, bottom = 1.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)
                    )

                    Card(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("history_page/$studentID")
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
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.Default
                                )
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = Color.White)
                    .size(500.dp, 205.dp),
            ){
                Column {

                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )

                    Card(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("staff_team_page")
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
                                contentDescription = "Staff Team",
                                modifier = Modifier
                                    .size(25.dp)
                            )

                            Spacer(
                                modifier = Modifier
                                    .width(20.dp)
                            )

                            Text(
                                text = "Staff Team",
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.Default
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 1.dp, bottom = 1.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)
                    )

                    Card(
                        modifier = Modifier
                            .clickable {
                                navController.navigate("services_page")
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
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.Default
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 1.dp, bottom = 1.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Black)
                    )

                    Card(
                        modifier = Modifier
                            .clickable {

                                navController.navigate("about_us_page")
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
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight(600),
                                    fontFamily = FontFamily.Default
                                )
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier
                    .height(40.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Pages.Log_In)
                },
                modifier = Modifier
                    .size(width = 290.dp, height = 43.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E5128),
                    contentColor = Color.White
                )
            ) {

                Text(
                    text = "Logout",
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