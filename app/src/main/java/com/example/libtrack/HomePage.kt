package com.example.libtrack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier

@Composable
fun HomePage(navHostController: NavHostController) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
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