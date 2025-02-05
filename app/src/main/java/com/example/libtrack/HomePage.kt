package com.example.libtrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navHostController: NavHostController) {

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "FVR - Library", fontWeight = FontWeight(700))
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = "Scan QR Code",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1E5128),
                    titleContentColor = Color.White
                )
            )
        }
    ) {paddingValues->

        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Row {
                Column {
                    Text(text = "Hello User!")
                    Text(text = "Let's start reading!")
                }
                IconButton(onClick = {

                }){
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Settings")
                }
            }



            LazyRow {
                items (1){
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                    Text(text = "Hello!")
                }
            }
    }

    }

}