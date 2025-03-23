package com.example.libtrack.authentication

import android.app.Application
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.libtrack.backend.SignupViewModel
import com.example.libtrack.backend.checkStudentID
import com.example.libtrack.backend.checkEmail
import com.example.libtrack.errorHandling.errorImage
import com.example.libtrack.errorHandling.passwordVisibilityFalseImage
import com.example.libtrack.errorHandling.passwordVisibilityTrueImage
import com.example.libtrack.errorHandling.successImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = PAGE 1 SIGN UP = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page1_SU(navHostController: NavHostController){

    // Text Fields / Text State / Page1_SU
    var firstNameTS by rememberSaveable { mutableStateOf("") }
    var lastNameTS by rememberSaveable { mutableStateOf("") }
    var studentIdTS by rememberSaveable { mutableStateOf("") }
    var passwordTS by rememberSaveable { mutableStateOf("") }
    var confirmPasswordTS by rememberSaveable { mutableStateOf("") }

    val validStudentId = studentIdTS.contains("-") && studentIdTS.contains("-") && studentIdTS.contains("-")

    // Completed all TFs
    val allCompletedPage1 = firstNameTS.isNotEmpty() &&
            lastNameTS.isNotEmpty() &&
            studentIdTS.isNotEmpty() &&
            passwordTS.isNotEmpty() &&
            confirmPasswordTS.isNotEmpty()

    // Booleans
    var isCompletePage1 by remember { mutableStateOf(true) }
    var isPasswordMatch by remember { mutableStateOf(true) }
    var isPasswordLength by remember { mutableStateOf(true) }
    var isValidStudentId by remember { mutableStateOf(true) }
    var isNewStudentId by remember { mutableStateOf(true) }

    // Focus Requester
    val firstNameFocusRequester = remember { FocusRequester() }
    val lastNameFocusRequester = remember { FocusRequester() }
    val studentIdFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    // Password
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Store Data to Variables
    val firstname = firstNameTS
    val lastname = lastNameTS
    val studentID = studentIdTS
    val password = passwordTS

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.navigate("log_in_page") }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {

            Text(
                text = "Create an Account",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )

            Text(
                text = "Step 1",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
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
                    .height(55.dp)
                    .focusRequester(firstNameFocusRequester),
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
                        text = "ex. Juan",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray

                    )
                },

                singleLine = true,
                value = firstNameTS,
                onValueChange = { firstNameTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {lastNameFocusRequester.requestFocus()}
                ),

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
                    .height(55.dp)
                    .focusRequester(lastNameFocusRequester),
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
                        text = "ex. Dela Cruz",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray
                    )
                },

                singleLine = true,
                value = lastNameTS,
                onValueChange = { lastNameTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {studentIdFocusRequester.requestFocus()}
                )
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
                        color = if(!isCompletePage1 || !isValidStudentId || !isNewStudentId) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp)
                    .focusRequester(studentIdFocusRequester),
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
                        text = "ex. 03-2324-000",
                        fontWeight = FontWeight(400),
                        color = Color.LightGray
                    )
                },

                singleLine = true,
                value = studentIdTS,
                onValueChange = { if (it.length <= 14) studentIdTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {passwordFocusRequester.requestFocus()}
                )
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
                    .height(55.dp)
                    .focusRequester(passwordFocusRequester),
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
                },                value = passwordTS,
                onValueChange = { passwordTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {confirmPasswordFocusRequester.requestFocus()}
                )
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

            // Hide Keyboard
            val keyboardController = LocalSoftwareKeyboardController.current

            TextField(
                modifier = Modifier
                    .border(
                        width = 1.2.dp,
                        color = if(!isCompletePage1 || !isPasswordMatch || !isPasswordLength) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .height(55.dp)
                    .focusRequester(confirmPasswordFocusRequester),
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
                value = confirmPasswordTS,
                onValueChange = { confirmPasswordTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                )
            )

            Spacer(
                modifier = Modifier.height(13.dp)
            )


            Row {

                if (!isCompletePage1 || !isPasswordMatch || !isPasswordLength || !isValidStudentId){
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
                    if(!isCompletePage1){
                        "Fill up all the requirements."
                    } else if (!isPasswordMatch){
                        "Password does not match."
                    } else if (!isValidStudentId){
                        "Invalid Student ID."
                    } else if (!isPasswordLength){
                        "Password must be at least \n 8 characters long."
                    }else if (!isNewStudentId){
                        "Student ID already has an Account."
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
                modifier = Modifier.height(13.dp)
            )

            val coroutineScope = rememberCoroutineScope()



            Button(
                onClick = {
                    if (allCompletedPage1 && passwordTS == confirmPasswordTS && passwordTS.length >= 8 && studentIdTS.length >= 10 && validStudentId) {
                        coroutineScope.launch(Dispatchers.IO) {
                            val dbResult = checkStudentID(studentIdTS)
                            withContext(Dispatchers.Main) {
                                if (dbResult) {
                                    isNewStudentId = false
                                } else {
                                    navHostController.navigate("sign_up_page2/$firstname/$lastname/$studentID/$password") // Use '/' to separate arguments
                                    isCompletePage1 = true
                                    isPasswordMatch = true
                                    isPasswordLength = true
                                    isValidStudentId = true
                                }
                            }
                        }

                    } else if (!allCompletedPage1) { // Incomplete Page
                        isCompletePage1 = false
                        isPasswordMatch = true
                        isPasswordLength = true
                        isValidStudentId = true
                    } else if (studentIdTS.length < 10 || !validStudentId){
                        isValidStudentId = false
                        isCompletePage1 = true
                        isPasswordMatch = true
                        isPasswordLength = true
                    } else if (passwordTS != confirmPasswordTS) { // Password Not Match
                        isPasswordMatch = false
                        isCompletePage1 = true
                        isPasswordLength = true
                        isValidStudentId = true
                    } else { // Password is less than 8 characters
                        isPasswordLength = false
                        isPasswordMatch = true
                        isCompletePage1 = true
                        isValidStudentId = true
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

// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = PAGE 2 SIGN UP = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page2_SU(
    navController: NavHostController,
    firstname: String,
    lastname: String,
    studentID: String,
    password: String
    ){

    val context = LocalContext.current
    val signupViewModel = remember { SignupViewModel(application = context.applicationContext as Application) } // Initialize ViewModel

    var schoolEmailTS by rememberSaveable { mutableStateOf("") }
    val validSchoolEmail = schoolEmailTS.contains(".up@phinmaed.com")
    var contactNumbTS by rememberSaveable { mutableStateOf("") }

    // Bachelor Degree
    val listYearLevelBD = listOf(
        "Freshman (1st Year)",
        "Sophomore (2nd Year)",
        "Junior (3rd Year)",
        "Senior (4th Year)",
    )

    // Undergrad
    val listYearLevelU = listOf(
        "Freshman (1st Year)",
        "Sophomore (2nd Year)",
        "Junior (3rd Year)",
        "Senior (4th Year)",
        "Super Senior (5th Year)"
    )

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
        "BS Education",
        "BS Electrical Engineering",
        "BS Hospitality Management",
        "BS Information Technology",
        "BS Management Accounting",
        "BS Mechanical Engineering",
        "BS Medical Laboratory",
        "BS Nursing",
        "BS Pharmacy",
        "BS Psychology",
        "BS Tourism Management"
    )

    var department: String

    var selectedProgram by rememberSaveable { mutableStateOf("Select Program") }
    var selectedYearLevel by rememberSaveable { mutableStateOf("Select Year Level") }

    var isYearLevelExpanded by remember { mutableStateOf(false) }
    var isProgramExpanded by remember { mutableStateOf(false) }

    var isChecked by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }

    val allCompletedPage2 = schoolEmailTS.isNotEmpty() &&
            contactNumbTS.isNotEmpty() &&
            listProgramCourse.contains(selectedProgram) &&
            (listYearLevelBD.contains(selectedYearLevel) || listYearLevelU.contains(selectedYearLevel))
            && isChecked


    // Error Handling / Booleans
    var isCompletePage2 by remember { mutableStateOf(true) }
    var isValidSchoolEmail by remember { mutableStateOf(true) }
    var isNewEmail by remember { mutableStateOf(true) }

    // Focus Requester
    val schoolEmailFocusRequester = remember { FocusRequester() }
    val contactNumbFocusRequester = remember { FocusRequester() }

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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(paddingValues).fillMaxSize()
        ) {

            Text(
                text = "Create an Account",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight(700)
                )
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Step 2",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
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
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                        .border(
                            width = 1.2.dp,
                            color = if (!isCompletePage2) Color.Red else Color(0xFFC1C1C1),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .width(350.dp),
                    value = selectedProgram,
                    textStyle = TextStyle(
                        fontWeight = FontWeight(400),
                        color = Color.Black,
                        fontSize = 17.sp
                    ),
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
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                        .border(
                            width = 1.2.dp,
                            color = if (!isCompletePage2) Color.Red else Color(0xFFC1C1C1),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .width(350.dp),
                    value = selectedYearLevel,
                    textStyle = TextStyle(
                        fontWeight = FontWeight(400),
                        color = Color.Black,
                        fontSize = 17.sp
                    ),
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
                    when (selectedProgram){
                        "BS Mechanical Engineering" -> {
                            listYearLevelU.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        selectedYearLevel = item
                                        isYearLevelExpanded = false
                                    }
                                )
                            }
                        }
                        "Select Program" -> {
                            DropdownMenuItem(
                                text = { Text(text = "Select a Program First", fontWeight = FontWeight(500)) },
                                onClick = {}
                            )
                        }
                        else -> {
                            listYearLevelBD.forEach { item ->
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
                        color = if (!isCompletePage2 || !isValidSchoolEmail || !isNewEmail) Color.Red else Color(0xFFC1C1C1),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .width(350.dp)
                    .focusRequester(schoolEmailFocusRequester),
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
                value = schoolEmailTS,
                onValueChange = { schoolEmailTS = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = {contactNumbFocusRequester.requestFocus()}
                )
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

            // Hide Keyboard
            val keyboardController = LocalSoftwareKeyboardController.current

             TextField(
                 modifier = Modifier
                     .border(
                         width = 1.2.dp,
                         color =  if (!isCompletePage2) Color.Red else Color(0xFFC1C1C1),
                         shape = RoundedCornerShape(15.dp)
                     )
                     .width(350.dp)
                     .focusRequester(contactNumbFocusRequester),
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
                         text = "09876543210",
                         fontWeight = FontWeight(400),
                         color = Color.LightGray
                     )
                 },

                 singleLine = true,
                 value = contactNumbTS,
                 onValueChange = { if (it.length <= 11) contactNumbTS = it },
                 keyboardOptions = KeyboardOptions(
                     keyboardType = KeyboardType.Number,
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
                    text = "Terms and Conditions",
                    style = TextStyle(
                        color = Color(0xFF006400),
                        fontSize = 15.sp,
                        fontWeight = FontWeight(400),
                        textDecoration = TextDecoration.Underline
                    )
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Row{
                if (!isCompletePage2 || !isValidSchoolEmail){
                    Image(
                        painter = painterResource(id = errorImage),
                        contentDescription = "Error Icon",
                        modifier = Modifier
                            .size(16.dp)
                    )
                } else {
                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp)
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text =
                    if (!isCompletePage2){
                        "Fill up all the requirements."
                    } else if (!isValidSchoolEmail){
                        "Enter a valid PHINMA Email"
                    } else if (!isNewEmail){
                        "PHINMA Email Already has an Account."
                    } else {
                        ""
                    },

                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 15.sp,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.Default,
                    ),
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            department = when (selectedProgram){
                "BS Medical Laboratory", "BS Nursing", "BS Pharmacy", "BS Psychology" -> "College of Allied Health and Sciences (CAHS)"
                "BS Criminology" -> "College of Criminal Justice Education (CCJE)"
                "BS Architecture", "BS Civil Engineering", "BS Computer Engineering", "BS Electrical Engineering", "BS Mechanical Engineering" -> "College of Engineering and Architecture (CEA)"
                "BA Political Science", "BS Education" -> "College of Education and Liberal Arts (CELA)"
                "Associate in Computer Technology", "BS Information Technology" -> "College of Information Technology Education (CITE)"
                "BS Accountancy", "BS Accounting Information System", "BS Business Admin Financial Management", "BS Business Admin Marketing Management",
                "BS Hospitality Management", "BS Management Accounting", "BS Tourism Management" -> "College of Management and Accountancy (CMA)"
                else  -> "Not Selected"
            }

            val coroutineScope = rememberCoroutineScope()


            Button(
                onClick = {
                    if(allCompletedPage2 && validSchoolEmail){

                        coroutineScope.launch(Dispatchers.IO) {
                            val dbResult = checkEmail(schoolEmailTS)
                            withContext(Dispatchers.Main) {
                                if (dbResult) { //error
                                    isNewEmail = false
                                } else { //correct
                                    isCompletePage2 = true
                                    isValidSchoolEmail = true
                                    signupViewModel.signupUser(
                                        firstname,
                                        lastname,
                                        studentID,
                                        schoolEmailTS, // As phinmaEmail
                                        password,
                                        selectedProgram, // As program
                                        selectedYearLevel, // As yearLevel
                                        department,
                                        contactNumbTS,
                                        navController
                                    )
                                }
                            }
                        }
                    } else if (!allCompletedPage2){
                        isCompletePage2 = false
                        isValidSchoolEmail = true
                    } else { // Invalid SchoolEmail
                        isCompletePage2 = true
                        isValidSchoolEmail = false
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
                    text = "Register"
                )
            }

            val errorMessage by signupViewModel.getErrorMessage().collectAsState(initial = null)

            errorMessage?.let {
                LaunchedEffect(it) {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }
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

// = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = COMPLETE SIGN UP = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =

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
                painter = painterResource(id = successImage),
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
                    navController.navigate("log_in_page"){
                        popUpTo("sign_up_complete_page"){
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

@Composable
fun Error_SU(navController: NavHostController){

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
                painter = painterResource(id = errorImage),
                contentDescription = "Error",
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )

            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            Text(
                text = "Sign-Up Error!",
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
                text = "Account Error. Student ID or Email already exists on an account.",
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
                    navController.navigate("log_in_page"){
                        popUpTo("sign_up_error_page"){
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
