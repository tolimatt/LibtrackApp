package com.example.libtrack

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BooksPage() {


    Column {

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Ok"
        )
        LazyColumn() {

            items (10){

                Row {
                    Card (
                        modifier = Modifier
                            .clickable {

                            }
                            .padding(7.dp)
                            .shadow(elevation = 4.dp)
                            .border(
                                width = 1.5.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .size(105.dp,140.dp)
                    ){
                        Column (
                            modifier = Modifier.padding(15.dp)
                        ){
                            Text(
                                "Placeholder"
                            )
                        }
                    }
                    Card (
                        modifier = Modifier
                            .clickable {

                            }
                            .padding(7.dp)
                            .shadow(elevation = 4.dp)
                            .border(
                                width = 1.5.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .size(105.dp,140.dp)
                    ){
                        Column (
                            modifier = Modifier.padding(15.dp)
                        ){
                            Text(
                                "Placeholder"
                            )
                        }
                    }
                    Card (
                        modifier = Modifier
                            .clickable {

                            }
                            .padding(7.dp)
                            .shadow(elevation = 4.dp)
                            .border(
                                width = 1.5.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .size(105.dp,140.dp)
                    ){
                        Column (
                            modifier = Modifier.padding(15.dp)
                        ){
                            Text(
                                "Placeholder"
                            )
                        }
                    }
                }

            }
        }
    }




}