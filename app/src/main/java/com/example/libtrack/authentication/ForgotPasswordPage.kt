package com.example.libtrack.authentication

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.libtrack.navFunctions.Pages
import com.example.libtrack.R
import com.example.libtrack.backend.SEND_OTP_URL_PATH
import com.example.libtrack.backend.SERVER_IP
import com.example.libtrack.backend.UPDATE_PASSWORD_URL_PATH
import com.example.libtrack.backend.VERIFY_OTP_URL_PATH
import com.example.libtrack.errorHandling.errorImage
import org.json.JSONObject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page1_FP(navController: NavHostController){


    val context = LocalContext.current

    var fpSchoolEmailTS by remember { mutableStateOf("") }

    var isEmailNotNull by remember { mutableStateOf(true) }
    var isRegisteredEmail by remember { mutableStateOf(true) }

    val schoolEmailFocusRequester = remember { FocusRequester() }

    // Hide Keyboard
    val keyboardController = LocalSoftwareKeyboardController.current



    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {

            Text(
                text = "Change Password",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Enter your School Email to Reset Password",
                style = TextStyle(
                    color = Color(0xFF727D83),
                    fontSize = 14.sp,
                )
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                modifier = Modifier
                    .offset(
                        (-123).dp, 0.dp),
                text = "SCHOOL EMAIL",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isRegisteredEmail || !isEmailNotNull) Color.Red else Color(0xFF727D83),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .focusRequester(schoolEmailFocusRequester),
                value = fpSchoolEmailTS,
                onValueChange = { fpSchoolEmailTS = it},
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
                        text = "ex. juan.delacruz.up@phinma.com",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                )
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row {

                if (!isRegisteredEmail || !isEmailNotNull){

                    Image(
                        painter = painterResource(id = errorImage),
                        contentDescription = "Error Icon",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = if (!isRegisteredEmail) "School Email is not Registered" else if (!isEmailNotNull) "Please Enter Your School Email"  else "",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700)
                        )
                    )
                }

            }



            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Button(
                onClick = {

                    if (fpSchoolEmailTS.contains(".up@phinmaed.com")){
                        sendOTP(
                            email = fpSchoolEmailTS,
                            context = context){success ->
                            isRegisteredEmail = true
                            isEmailNotNull = true
                            if (success) {

                                navController.navigate(Pages.Forgot_Password_Page2 + "/$fpSchoolEmailTS")
                            }
                        }
                    } else if(fpSchoolEmailTS == ""){
                        isEmailNotNull = false
                    } else{
                        isRegisteredEmail = false
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
                    text = "Reset Password",
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


fun sendOTP(email: String, context: Context, onSuccess: (Boolean) -> Unit) {
    val url = SERVER_IP+SEND_OTP_URL_PATH

    val jsonObject = JSONObject()
    jsonObject.put("email", email)

    val request = JsonObjectRequest(
        Request.Method.POST, // Explicitly use Volley's Method
        url, jsonObject,
        { response ->
            val status = response.getString("status")
            if (status == "success") {
                Toast.makeText(context, "OTP sent to $email", Toast.LENGTH_SHORT).show()
                onSuccess(true)
            } else {
                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
                onSuccess(false)
            }
        },
        { error ->
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            onSuccess(false)
        }
    )

    Volley.newRequestQueue(context).add(request)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page2_FP(navController: NavHostController, email: String){

    val context = LocalContext.current
    var otp by remember { mutableStateOf("") }


    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {

            Text(
                text = "Check your Email",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Text(
                text = "We sent an Email to (email), Enter the 5 Digit Code mentioned on the Email",
                style = TextStyle(
                    color = Color(0xFF727D83),
                    fontSize = 14.sp,
                ),
                textAlign = TextAlign.Center
            )

            TextField(
                value = otp,
                onValueChange = {
                    if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                        otp = it
                    }
                },
                label = { Text("5-Digit Code") },
                singleLine = true,
                isError = otp.length > 5
            )

            Button(
                onClick = {
                    verifyOTP(
                        email = email,
                        otp = otp,
                        context = context){success ->
                        if (success){
                            navController.navigate(Pages.Forgot_Password_Page3 + "/$email")
                        }
                    }
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
                    text = "Verify",
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

fun verifyOTP(email: String, otp: String, context: Context, onSuccess: (Boolean) -> Unit) {
    val url = SERVER_IP+ VERIFY_OTP_URL_PATH

    val jsonObject = JSONObject()
    jsonObject.put("email", email)
    jsonObject.put("otp", otp)

    val request = JsonObjectRequest(
        Request.Method.POST, url, jsonObject,
        { response ->
            val status = response.getString("status")
            if (status == "success") {
                Toast.makeText(context, "OTP verified!", Toast.LENGTH_SHORT).show()
                onSuccess(true)
            } else {
                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
            }
        },
        { error ->
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    )

    Volley.newRequestQueue(context).add(request)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page3_FP(navController: NavHostController, email: String){

    val context = LocalContext.current
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {

            Text(
                text = "Reset Password",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Text(
                text = "Create a new Password. Ensure it Differs from your previous password",
                style = TextStyle(
                    color = Color(0xFF727D83),
                    fontSize = 14.sp,
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "NEW PASSWORD"
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = Color(0xFF727D83),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .width(350.dp),
                value = newPassword,
                onValueChange = { newPassword = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Text(
                text = "CONFIRM NEW PASSWORD"
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = Color(0xFF727D83),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .width(350.dp),
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )

            Button(
                onClick = {
                    if (newPassword == confirmPassword) {
                        updatePassword(email, newPassword, context) { success ->
                            if (success) {
                                navController.navigate(Pages.Forgot_Password_Complete)
                            }
                        }
                    } else {
                        Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                    }
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
                    text = "Confirm",
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

fun updatePassword(email: String, newPassword: String, context: Context, onSuccess: (Boolean) -> Unit) {
    val url = SERVER_IP + UPDATE_PASSWORD_URL_PATH

    val jsonObject = JSONObject()
    jsonObject.put("email", email)
    jsonObject.put("new_password", newPassword)

    val request = JsonObjectRequest(
        Request.Method.POST, url, jsonObject,
        { response ->
            val status = response.getString("status")
            if (status == "success") {
                Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                onSuccess(true)
            } else {
                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show()
            }
        },
        { error ->
            Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
        }
    )

    Volley.newRequestQueue(context).add(request)
}


@Composable
fun Complete_FP(navHostController: NavHostController){
    Scaffold (
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.success),
                contentDescription = "Success",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Text(
                text = "Password Changed Successfully",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Text(
                text = "Your Password has been changed Successfully",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                ),
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier
                    .height(80.dp)
            )

            Button(

                onClick = {
                    navHostController.navigate(Pages.Log_In)
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
                    text = "Back to LogIn",
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