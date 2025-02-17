package com.example.libtrack

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page1_SU(navHostController: NavHostController){

    // Text Fields / Text State / Page1_SU
    var firstNameTS by rememberSaveable { mutableStateOf("") }
    var lastNameTS by rememberSaveable { mutableStateOf("") }
    var studentIdTS by rememberSaveable { mutableStateOf("") }
    var passwordTS by rememberSaveable { mutableStateOf("") }
    var confirmPasswordTS by rememberSaveable { mutableStateOf("") }

    // Completed all TFs
    val allCompletedPage1 = firstNameTS.isNotEmpty() &&
            lastNameTS.isNotEmpty() &&
            studentIdTS.isNotEmpty() &&
            passwordTS.isNotEmpty() &&
            confirmPasswordTS.isNotEmpty()

    var isCompletePage1 by remember { mutableStateOf(true) }
    var isPasswordMatch by remember { mutableStateOf(true) }
    var isPasswordLength by remember { mutableStateOf(true) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate(Pages.Log_In) }) {
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
                text = "Create an Account",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text = "Step 1",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 23.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )

            Text(
                textAlign = TextAlign.Center,
                text = "Welcome! Please fill in your details to create an account. Ensure that your student ID and password are correct before proceeding.",
                style = TextStyle(
                    color = Color(0xFF727D83),
                    fontSize = 14.sp,
                )
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            // ------------------------------------------------------------ FIRST NAME ------------------------------------------------------------

            Text(
                modifier = Modifier.offset(
                    (-130).dp, 0.dp
                ),
                text = "FIRST NAME",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isCompletePage1) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
                value = firstNameTS,
                onValueChange = { firstNameTS = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "First Name",
                        fontWeight = FontWeight(400)
                    )
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------ LAST NAME ------------------------------------------------------------

            Text(
                modifier = Modifier.offset(
                    (-130).dp, 0.dp
                ),
                text = "LAST NAME",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isCompletePage1) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
                value = lastNameTS,
                onValueChange = { lastNameTS = it  },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Last Name",
                        fontWeight = FontWeight(400)
                    )
                },
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------ STUDENT ID ------------------------------------------------------------

            Text(
                modifier = Modifier.offset(
                    (-130).dp, 0.dp
                ),
                text = "STUDENT ID",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isCompletePage1) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
                value = studentIdTS,
                onValueChange = { if (it.length <= 14) studentIdTS = it  },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Student ID",
                        fontWeight = FontWeight(400)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------ PASSWORD ------------------------------------------------------------

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
                        color = if(!isCompletePage1 || !isPasswordMatch || !isPasswordLength) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
                value = passwordTS,
                onValueChange = { passwordTS = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Password",
                        fontWeight = FontWeight(400)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Text(
                modifier = Modifier.offset(
                    (-40).dp, 0.dp
                ),
                text = "• Password must be at least 12 characters",
                fontSize = 13.sp,
                color = Color(0xFF727D83)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------ CONFIRM PASSWORD ------------------------------------------------------------

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
                        color = if(!isCompletePage1 || !isPasswordMatch || !isPasswordLength) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp),
                value = confirmPasswordTS,
                onValueChange = { confirmPasswordTS = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "Confirm Password",
                        fontWeight = FontWeight(400)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            if (!isCompletePage1){
                Text(
                    modifier = Modifier.offset(
                        (0).dp, 15.dp
                    ),
                    text ="Fill up all the requirements.",
                    fontSize = 16.sp,
                    color = Color.Red,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Gray,
                            offset = Offset(0.1f, 0.1f),
                            blurRadius = 5f
                        ),
                        fontWeight = FontWeight(400)
                    ),
                )
            }
            if (!isPasswordMatch || !isPasswordLength){
                Text(
                    modifier = Modifier.offset(
                        (0).dp, 15.dp
                    ),
                    text ="Invalid Password.",
                    fontSize = 16.sp,
                    color = Color.Red,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Gray,
                            offset = Offset(0.1f, 0.1f),
                            blurRadius = 5f
                        ),
                        fontWeight = FontWeight(400)
                    ),
                )
            }

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            val firstname = firstNameTS
            val lastname = lastNameTS
            val studentId = studentIdTS
            val password = passwordTS

            Button(
                onClick = {
                    navHostController.navigate("Page2_SU/${firstname} ${lastname}${studentId}${password}")

                    /*if (allCompletedPage1 && passwordTS == confirmPasswordTS && passwordTS.length >= 12) {
                        isCompletePage1 = true
                        isPasswordMatch = true
                        navHostController.navigate("Page2_SU/${firstname} ${lastname}${studentId}${password} ")
                    } else if (!allCompletedPage1) { // Incomplete Page
                        isCompletePage1 = false
                        isPasswordMatch = true
                        isPasswordLength = true
                    } else if (passwordTS != confirmPasswordTS) { // Password Not Match
                        isPasswordMatch = false
                        isCompletePage1 = true
                        isPasswordLength = false
                    } else {
                        isPasswordLength = false
                        isPasswordMatch = true
                        isCompletePage1 = true
                    }*/
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
                    text = "Proceed",
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
fun Page2_SU(
    navController: NavHostController,
    firstname: String,
    lastname: String,
    studentId: String,
    password: String
    ){

    val context = LocalContext.current
    val signupViewModel = remember { SignupViewModel(context) } // Initialize ViewModel

    var schoolEmailTS by rememberSaveable { mutableStateOf("") }
    val validSchoolEmail = schoolEmailTS.contains("@") && schoolEmailTS.contains(".")
    var contactNumbTS by rememberSaveable { mutableStateOf("") }

    val listYearLevel = listOf(
        "Freshman (1st Year)",
        "Sophomore (2nd Year)",
        "Junior (3rd Year)",
        "Senior (4th Year)",
        "Super Senior (5th Year)")
    val listProgramCourse = listOf(
        "Associate in Computer Technology",
        "BA Political Science",
        "BS Accountancy",
        "BS Accounting Information System",
        "BS Architecture",
        "BS Business Admin Financial Management",
        "BS Business Admin Marketing Management",
        "BS Civil Engineering",
        "BS Computer Engineering",
        "BS Criminology",
        "BS Electrical Engineering",
        "BS Hospitality Management",
        "BS Information Technology",
        "BS Management Accounting",
        "BS Mechanical Engineering",
        "BS Medical Laboratory",
        "BS Nursing",
        "BS Pharmacy",
        "BS Psychology",
        "BS Tourism Management",)


    var selectedProgram by rememberSaveable { mutableStateOf("") }
    var selectedYearLevel by rememberSaveable { mutableStateOf("") }

    var isYearLevelExpanded by remember { mutableStateOf(false) }
    var isProgramExpanded by remember { mutableStateOf(false) }

    var isChecked by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }

    val allCompletedPage2 = schoolEmailTS.isNotEmpty() &&
            validSchoolEmail &&
            contactNumbTS.isNotEmpty() &&
            selectedProgram != null &&
            selectedYearLevel != null &&
            isChecked

    var isCompletePage2 by remember { mutableStateOf(true) }
    var isValidSchoolEmail by remember { mutableStateOf(true) }


    Scaffold(
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
    ){ paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {

            Text(
                text = "Create an Account",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Step 2",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 23.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                textAlign = TextAlign.Center,
                text = "Almost there! Please provide your email, contact details, and academic information to complete your account setup",
                style = TextStyle(
                    color = Color(0xFF727D83),
                    fontSize = 14.sp,
                )
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // ------------------------------------------------------------ DROP DOWN MENUS ------------------------------------------------------------

            Text(
                modifier = Modifier.offset(
                    (-130).dp, 0.dp
                ),
                text = "YEAR LEVEL",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            // YEAR LEVEL
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .width(350.dp),
                expanded = isYearLevelExpanded,
                onExpandedChange = { isYearLevelExpanded = !isYearLevelExpanded }
            ) {

                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .border(
                            width = 1.2.dp,
                            color = if (!isCompletePage2) Color.Red else Color(0xFFC1C1C1),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .width(350.dp),
                    value = selectedYearLevel ?: "Year Level",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isYearLevelExpanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                )

                ExposedDropdownMenu(
                    expanded = isYearLevelExpanded,
                    onDismissRequest = { isYearLevelExpanded = false },
                ) {
                    listYearLevel.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedYearLevel = item
                                isYearLevelExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                modifier = Modifier.offset(
                    (-135).dp, 0.dp
                ),
                text = "PROGRAM",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            // PROGRAM
            ExposedDropdownMenuBox(
                expanded = isProgramExpanded,
                onExpandedChange = { isProgramExpanded = !isProgramExpanded },
                modifier = Modifier
                    .width(350.dp),

            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .border(
                            width = 1.2.dp,
                            color = if (!isCompletePage2) Color.Red else Color(0xFFC1C1C1),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .width(350.dp),
                    value = selectedProgram ?: "Program",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isProgramExpanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                )

                ExposedDropdownMenu(
                    expanded = isProgramExpanded,
                    onDismissRequest = { isProgramExpanded = false }
                ) {

                    listProgramCourse.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedProgram = item
                                isProgramExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------ SCHOOL EMAIL ------------------------------------------------------------

            Text(
                modifier = Modifier.offset(
                    (-122).dp, 0.dp
                ),
                text = "SCHOOL EMAIL",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if (!isCompletePage2 || !isValidSchoolEmail) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp),
                value = schoolEmailTS,
                onValueChange = { schoolEmailTS = it },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "School Email",
                        fontWeight = FontWeight(400)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            // ------------------------------------------------------------ CONTACT NUMBER ------------------------------------------------------------

            Text(
                modifier = Modifier.offset(
                    (-112).dp, 0.dp
                ),
                text = "CONTACT NUMBER",
                fontSize = 12.sp,
                color = Color(0xFF727D83)
            )

             TextField(
                 modifier = Modifier
                     .border(
                         width = 1.2.dp,
                         color =  if (!isCompletePage2) Color.Red else Color(0xFFC1C1C1),
                         shape = RoundedCornerShape(15.dp)
                     )
                     .width(350.dp),
                 value = contactNumbTS,
                 onValueChange = { contactNumbTS = it },
                 colors = TextFieldDefaults.colors(
                     focusedContainerColor = Color.Transparent,
                     unfocusedContainerColor = Color.Transparent,
                     focusedIndicatorColor = Color.Transparent,
                     unfocusedIndicatorColor = Color.Transparent
                 ),
                 placeholder = {
                     Text(
                         text = "Contact Number",
                         fontWeight = FontWeight(400)
                     )
                 },
                 keyboardOptions = KeyboardOptions(
                     keyboardType = KeyboardType.Number,
                     imeAction = ImeAction.Done
                 ),
                 singleLine = true
             )

            

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Row {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it},
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF72AF7B),
                        uncheckedColor = if (!isCompletePage2) Color.Red else Color(0xFF72AF7B)
                    )
                )

                Text(
                    modifier = Modifier.offset(
                        0.dp, 15.dp
                    ),
                    text = "I hereby accept the ",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400)
                    )
                )

                val interactionSource = remember { MutableInteractionSource() }
                Text(
                    modifier = Modifier
                        .clickable (
                            interactionSource = interactionSource,
                            indication = null
                        ){
                            isShowDialog = true

                        }
                        .offset(
                            0.dp,15.dp
                        ),
                    text = "T&C ",
                    style = TextStyle(
                        color = Color(0xFF006400),
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        textDecoration = TextDecoration.Underline
                    )
                )
                Text(
                    modifier = Modifier
                        .offset(0.dp,15.dp),
                    text = "of Liquid ",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400)
                    )
                )
            }

            if (!isCompletePage2){
                Text(
                    modifier = Modifier.offset(
                        (0).dp, 20.dp
                    ),
                    text ="Fill up all the requirements.",
                    fontSize = 16.sp,
                    color = Color.Red,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Gray,
                            offset = Offset(0.1f, 0.1f),
                            blurRadius = 5f
                        ),
                        fontWeight = FontWeight(400)
                    ),
                )
            }

            if (!isValidSchoolEmail){
                Text(
                    modifier = Modifier.offset(
                        (0).dp, 20.dp
                    ),
                    text ="Enter a valid Email.",
                    fontSize = 16.sp,
                    color = Color.Red,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Gray,
                            offset = Offset(0.1f, 0.1f),
                            blurRadius = 5f
                        ),
                        fontWeight = FontWeight(400)
                    ),
                )
            }

            Spacer(
                modifier = Modifier.height(58.dp)
            )

            Button(
                onClick = {

                    signupViewModel.signupUser(firstname, lastname, studentId, password, selectedYearLevel, selectedProgram, schoolEmailTS, contactNumbTS)
                    /*if(allCompletedPage2){
                        navController.navigate(Pages.Sign_Up_Complete)
                        // Get all values of firstName, lastName, studentId, password, yearLevel, program, schoolEmail, contactNumb
                        // Insert to DB
                    } else if (!validSchoolEmail){
                        isValidSchoolEmail = false
                        isCompletePage2 = true
                    } else {
                        isCompletePage2 = false
                        isValidSchoolEmail = true
                    }*/
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
                    text = "Register"
                )

            }



            if (isShowDialog) {
                AlertDialog(
                    onDismissRequest = { isShowDialog = false },
                    title = {
                        Text(
                            text = "Terms and Conditions for Using LibTrack",
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 23.sp,
                                fontWeight = FontWeight(600)
                            )
                        )
                    },
                    text = {
                        LazyColumn {
                            items(1){
                                Text(
                                    text = "Welcome to LibTracker! By accessing or using LibTracker the \"Service\", you agree to be bound by these Terms and Conditions \"Terms\". Please read them carefully before using the Service. If you do not agree to these Terms, you may not use LibTracker.\n" +
                                            "By using LibTracker, you confirm that you are at least a bonded PHINMA - University of Pangasinan student to enter into this agreement. If you are using the Service on behalf of an organization, you represent that you have the authority to bind that organization to these Terms.\n" +
                                            "LibTracker is a library management and tracking tool designed to help users organize, monitor, and manage library resources.\n" +
                                            "You agree to:\n\n" +
                                            "- Use LibTracker only for lawful purposes and in compliance with all applicable laws and regulations.\n\n" +
                                            "- Provide accurate and complete information when creating an account or using the Service.\n\n" +
                                            "- Maintain the confidentiality of your account credentials and be responsible for all activities under your account.\n\n" +
                                            "- Not use the Service to infringe on the intellectual property rights of others or engage in any harmful, abusive, or fraudulent activities.\n\n" +
                                            "Your use of LibTracker is subject to our Privacy Policy, which explains how we collect, use, and protect your personal information. By using the Service, you consent to the practices described in the Privacy Policy.\n" +
                                            "By using LibTracker, you acknowledge that you have read, understood, and agree to these Terms and Conditions. Thank you for choosing LibTracker!"
                                )
                            }

                        }

                    },
                    confirmButton = {
                        Button(
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF72AF7B),
                                contentColor = Color.White
                            ),
                            onClick = {
                                isShowDialog = false
                            },
                        ) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun Complete_SU(navController: NavHostController){

    Scaffold (
        modifier = Modifier.fillMaxSize()
    ){ paddingValues ->

        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
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
                text = "Sign-Up Successful!",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                textAlign = TextAlign.Center,
                text = "Welcome to the LibTrack! Your account has been successfully created. Enjoy exploring our collection.",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400)
                )
            )

            Spacer(
                modifier = Modifier.height(80.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Pages.Log_In){
                        popUpTo(Pages.Sign_Up_Complete){
                            inclusive = true}
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
                    text = "Back to Login",
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


class SignupViewModel(private val context: Context) : androidx.lifecycle.ViewModel() {
    var connectedStatus = MutableStateFlow("Not connected") // Status in ViewModel

    fun signupUser(
        firstname: String,
        lastname: String,
        studentid: String,
        password: String,
        yearlevel: String,
        program: String,
        schoolemail: String,
        contactnumber: String) {

        val userData = UserData(
            firstname,
            lastname,
            studentid,
            password,
            yearlevel,
            program,
            schoolemail,
            contactnumber)

        val json = Gson().toJson(userData)
        Log.d("Request Body", json)

        viewModelScope.launch {
            try {
                val response = RetrofitSignup.api.signup(userData)

                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val status = apiResponse?.status ?: "Unknown status"
                    connectedStatus.value = "Connected: $status" // Update status in ViewModel
                    Log.d("Server Response", status)
                } else {
                    connectedStatus.value = "Error: ${response.code()} - ${response.message()}" // Update status
                    Log.e("Server Error", "Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: IOException) {
                connectedStatus.value = "Network Error: ${e.message}" // Update status
                Log.e("Network Error", e.message.toString())
            } catch (e: Exception) {
                connectedStatus.value = "Request failed: ${e.message}" // Update status
                Log.e("Request Error", e.message.toString())
            }
        }
    }
}

data class ApiResponseSignup(
    val status: String
)

data class UserData(
    val firstname: String,
    val lastname: String,
    val studentid: String,
    val password: String,
    val yearlevel: String,
    val program: String,
    val schoolemail: String,
    val contactnumber: String
)

interface SignupServer {
    @POST("libTrack/signup.php")
    suspend fun signup(@Body userData: UserData): Response<ApiResponseSignup>
}

object RetrofitSignup {
    private const val BASE_URL = "http://192.168.1.59/" // IPV4 Address of the connection

    val api: SignupServer by lazy {
        val gson = GsonBuilder().setLenient().create()
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        retrofit.create(SignupServer::class.java)
    }
}