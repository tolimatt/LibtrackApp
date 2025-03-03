package com.example.libtrack.pagesMain

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libtrack.backend.SERVER_IP
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

@Composable
fun HomePage(studentNumber: String) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var firstName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(studentNumber) {
        scope.launch {
            try {
                val response = RetrofitAccountName.apiService.getFirstName(studentNumber)
                response.enqueue(object : Callback<AccountResponse> {
                    override fun onResponse(
                        call: Call<AccountResponse>,
                        response: Response<AccountResponse>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            firstName = response.body()!!.firstName
                        } else {
                            Toast.makeText(context, "Failed to fetch first name. Code:${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                        Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } catch (e: Exception) {
                Toast.makeText(context, "An unexpected error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LazyColumn (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(1){

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                modifier = Modifier
                    .offset(3.dp, 0.dp),
                text = if (firstName != null){"Hello $firstName!"} else{"Hello ...!"},
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                modifier = Modifier
                    .offset(3.dp, 0.dp),
                text = "Let's start reading!",
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color(0xFF727D83)
                )
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // Top Rated Books
            Text(
                modifier = Modifier
                    .offset(3.dp, 0.dp),
                text = "Top Rated Books",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.Default
                )
            )

            LazyRow {
                items (10){
                    Column {
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
                        Text(
                            text = "Never Mind\nDito pala dapat \nyung placeholder",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            // Top Rated Books
            Text(
                modifier = Modifier
                    .offset(3.dp, 0.dp),
                text = "Course Related Books",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.Default
                )
            )

            LazyRow {

                items (10){
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

            Spacer(
                    modifier = Modifier.height(10.dp)
            )

            // Top Rated Books
            Text(
                modifier = Modifier
                    .offset(3.dp, 0.dp),
                text = "Off Topic Books",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.Default
                )
            )

            LazyRow {
                items (10){
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

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Top Rated Books
            Text(
                modifier = Modifier
                    .offset(3.dp, 0.dp),
                text = "Off Topic Books",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.Default
                )
            )

            LazyRow {
                items (10){
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

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // Top Rated Books
            Text(
                modifier = Modifier
                    .offset(3.dp, 0.dp),
                text = "Off Topic Books",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight(600),
                    fontFamily = FontFamily.Default
                )
            )

            LazyRow {
                items (10){
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


interface ApiService {
    @GET("hello/tests.php") // Replace with your PHP endpoint
    fun getFirstName(@Query("id") accountId: String): Call<AccountResponse>
}

data class AccountResponse(
    val firstName: String
)

object RetrofitAccountName {

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(SERVER_IP)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
