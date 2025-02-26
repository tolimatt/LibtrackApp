package com.example.libtrack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun SettingsPage(){

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
                    painter = painterResource(id = R.drawable.personal_info),
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
                        painter = painterResource(id = R.drawable.notifications),
                        contentDescription = "Notifications",
                        modifier = Modifier
                            .size(25.dp)
                    )

                    Spacer(
                        modifier = Modifier
                            .width(20.dp)
                    )

                    Text(
                        text = "Notifications",
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
                    painter = painterResource(id = R.drawable.change_password),
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
                        painter = painterResource(id = R.drawable.history),
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
                    painter = painterResource(id = R.drawable.about_us),
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

        Box(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )







        }

}

data class SettingsOptions(val icon: Int)