package com.example.libtrack.authentication

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.libtrack.R
import com.example.libtrack.backend.sendOTP
import com.example.libtrack.backend.updatePassword
import com.example.libtrack.backend.verifyOTP
import com.example.libtrack.errorHandling.errorImage
import com.example.libtrack.errorHandling.passwordVisibilityFalseImage
import com.example.libtrack.errorHandling.passwordVisibilityTrueImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page1_FP(navController: NavHostController){

    var lastClickTime by remember { mutableLongStateOf(0L) } // Track last click time


    val context = LocalContext.current

    var fpSchoolEmailTS by remember { mutableStateOf("") }

    var isEmailNotNull by remember { mutableStateOf(true) }
    var isRegisteredEmail by remember { mutableStateOf(true) }

    val schoolEmailFocusRequester = remember { FocusRequester() }

    var isLoading by remember { mutableStateOf(false) }

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
                    IconButton(onClick = {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastClickTime > 500) { // 500ms debounce
                            lastClickTime = currentTime
                            navController.popBackStack() // Navigate back
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
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
                        isLoading = true
                        sendOTP(
                            email = fpSchoolEmailTS,
                            context = context
                        ){success ->
                            isLoading = false
                            isRegisteredEmail = true
                            isEmailNotNull = true
                            if (success) {
                                navController.navigate("forgot_password_page2/$fpSchoolEmailTS")
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
                ),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
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
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page2_FP(navController: NavHostController, email: String){

    val context = LocalContext.current
    var otp by remember { mutableStateOf("") }

    var isCorrectOTP by remember { mutableStateOf(true) }


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

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "We sent an Email to (email), Enter the 5 Digit Code mentioned on the Email",
                style = TextStyle(
                    color = Color(0xFF727D83),
                    fontSize = 14.sp,
                ),
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                modifier = Modifier
                    .offset(
                        (-123).dp, 0.dp),
                text = "OTP CODE",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isCorrectOTP) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
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
                        text = "Enter the five digit code",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray
                    )
                },

                singleLine = true,
                value = otp,
                onValueChange = {
                    if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                        otp = it
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row {

                if (!isCorrectOTP){
                    Image(
                        painter = painterResource(id = errorImage),
                        contentDescription = "Error Icon",
                        modifier = Modifier.size(20.dp)
                    )
                } else{
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text =
                    if(!isCorrectOTP){
                        "Incorrect OTP."
                    } else{
                        ""
                    },
                    fontSize = 15.sp,
                    color = Color.Red,
                    style = TextStyle(
                        fontWeight = FontWeight(700)
                    ),
                )
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Button(
                onClick = {
                    verifyOTP(
                        email = email,
                        otp = otp,
                        context = context
                    ){success ->
                        if (success){
                            navController.navigate("forgot_password_page3/$email")
                        } else{
                            isCorrectOTP = false
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page3_FP(navController: NavHostController, email: String){

    val context = LocalContext.current
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var isPasswordValid by remember { mutableStateOf(true) }
    var isPasswordMatch by remember { mutableStateOf(true) }

    var isPasswordVisible by remember { mutableStateOf(false) }

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

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Create a new Password. Ensure it Differs from your previous password",
                style = TextStyle(
                    color = Color(0xFF727D83),
                    fontSize = 14.sp,
                ),
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                modifier = Modifier.offset(
                    (-130).dp, 0.dp
                ),
                text = "PASSWORD",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isPasswordValid || !isPasswordMatch) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
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
                        text = "Password",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray
                    )
                },
                singleLine = true,
                visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(id = if (isPasswordVisible) passwordVisibilityTrueImage else passwordVisibilityFalseImage),
                            contentDescription = "Password Visibility Toggle"
                        )
                    }
                },
                value = newPassword,
                onValueChange = { newPassword = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
            )

            Text(
                modifier = Modifier.offset(
                    (-40).dp, 0.dp
                ),
                text = "• Password must be at least 8 characters",
                fontSize = 13.sp,
                color = Color(0xFF727D83)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                modifier = Modifier.offset(
                    (-104).dp, 0.dp
                ),
                text = "CONFIRM PASSWORD",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isPasswordMatch || !isPasswordValid) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
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
                        text = "Confirm Password",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray
                    )
                },

                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row {

                if (!isPasswordValid || !isPasswordMatch){
                    Image(
                        painter = painterResource(id = errorImage),
                        contentDescription = "Error Icon",
                        modifier = Modifier.size(20.dp)
                    )
                } else{
                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                }

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text =
                    if(!isPasswordValid){
                        "Password must be at least 8 characters long."
                    } else if(!isPasswordMatch){
                        "Password does not match."
                    }else {
                        ""
                    },
                    fontSize = 15.sp,
                    color = Color.Red,
                    style = TextStyle(
                        fontWeight = FontWeight(700)
                    ),
                )
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Button(
                onClick = {
                    if (newPassword == confirmPassword && newPassword.length >= 8) {
                        updatePassword(email, newPassword, context) { success ->
                            if (success) {
                                navController.navigate("forgot_password_complete_page"){
                                    popUpTo(0){
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    } else if (newPassword.length < 8){
                        isPasswordValid = false
                    } else {
                        isPasswordMatch = false
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
                    navHostController.navigate("log_in_page"){
                        popUpTo(0) {
                            inclusive = true
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