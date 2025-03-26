package com.example.libtrack.pagesMain

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.libtrack.R
import com.example.libtrack.backend.AttendanceItem
import com.example.libtrack.backend.AttendanceViewModel
import com.example.libtrack.backend.HistoryItem
import com.example.libtrack.backend.HistoryViewModel
import com.example.libtrack.backend.NotificationLibrary
import com.example.libtrack.backend.RetrofitAccountName
import com.example.libtrack.backend.UserDetails
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoPage(
    studentID: String,
    navController: NavHostController
){
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var firstName by remember { mutableStateOf<String?>("") }
    var lastName by remember { mutableStateOf<String?>("") }
    var phinmaEmail by remember { mutableStateOf<String?>("") }
    var contactNumber by remember { mutableStateOf<String?>("") }
    var yearLevel by remember { mutableStateOf<String?>("") }
    var program by remember { mutableStateOf<String?>("") }
    var department by remember { mutableStateOf<String?>("") }

    LaunchedEffect(studentID) {

        scope.launch {
            try {
                val response = RetrofitAccountName.apiServiceStudentDetails.getFirstName(studentID)
                response.enqueue(object : Callback<UserDetails> {
                    override fun onResponse(
                        call: Call<UserDetails>,
                        response: Response<UserDetails>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            firstName = response.body()!!.firstName
                            lastName = response.body()!!.lastName
                            phinmaEmail = response.body()!!.schoolEmail
                            contactNumber = response.body()!!.contactNumber
                            yearLevel = response.body()!!.yearLevel
                            program = response.body()!!.program
                            department = response.body()!!.department
                        } else {
                            // Log the error response
                            Log.e("Personal Information", "Error response code: ${response.code()}")
                            Log.e("Personal Information", "Error response body: ${response.errorBody()?.string()}")
                            Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                        // Log the network error
                        Log.e("Personal Information", "Network error: ${t.message}")
                        Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

            } catch (e: Exception) {
                // Log any unexpected exceptions
                Log.e("Personal Information", "Unexpected error: ${e.message}")
                Toast.makeText(context, "An unexpected error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Personal Information",
                        fontWeight = FontWeight(700)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {

            Text(
                text = "First Name",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$firstName",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .background(Color.LightGray)
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Last Name",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$lastName",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .background(Color.LightGray)
                    .fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Student ID",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = studentID,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Contact Number",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "0$contactNumber",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Year Level",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$yearLevel",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Program",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$program",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Department",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600)
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$department",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(1.dp)
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "PHINMAED Email",
                fontSize = 14.sp,
                color = Color(0xFF5E6366),
                fontWeight = FontWeight(600),
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = "$phinmaEmail",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight(600),
                modifier = Modifier.offset(10.dp, 0.dp)
            )

            Spacer(
                modifier = Modifier.height(2.dp)
            )

            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryPage(studentID: String, navController: NavHostController){

    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var historyItems by remember { mutableStateOf<List<HistoryItem>?>(null) }
    val historyViewModel = remember{ HistoryViewModel() }

    var attendanceItems by remember { mutableStateOf<List<AttendanceItem>?>(null) }
    val attendanceViewModel = remember{ AttendanceViewModel() }

    var isShowBorrowing by remember { mutableStateOf(false) }
    var isShowBorrowed by remember { mutableStateOf(false) }
    var isShowAttendance by remember { mutableStateOf(false) }

    isShowBorrowing = true

    LaunchedEffect(studentID) {
        scope.launch {
            try {
                attendanceItems = attendanceViewModel.fetchStudentAttendance(studentID)
                historyItems = historyViewModel.fetchStudentHistory(studentID)
                isLoading = false
            } catch (e: Exception) {
                errorMessage = "Error fetching history: ${e.message}"
                isLoading = false
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "History",
                        fontWeight = FontWeight(700)
                    )
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Row{

                Button(
                    onClick = {
                        isShowBorrowing = true
                        isShowBorrowed = false
                        isShowAttendance = false
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(isShowBorrowing) Color(0xFF72AF7B) else Color.Gray.copy(alpha = 0.4f),
                        contentColor = Color.White
                    )
                ){
                    Text("Borrowing")
                }

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Button(
                    onClick = {
                        isShowBorrowing = false
                        isShowBorrowed = true
                        isShowAttendance = false
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(isShowBorrowed) Color(0xFF72AF7B) else Color.Gray.copy(alpha = 0.4f),
                        contentColor = Color.White
                    )

                ){
                    Text("Borrowed")
                }

                Spacer(
                    modifier = Modifier.width(5.dp)
                )

                Button(
                    onClick = {
                        isShowBorrowing = false
                        isShowBorrowed = false
                        isShowAttendance = true
                    },

                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(isShowAttendance) Color(0xFF72AF7B) else Color.Gray.copy(alpha = 0.4f),
                        contentColor = Color.White
                    )

                ){
                    Text("Attendance")
                }
            }

            if (isLoading) {

                CircularProgressIndicator(
                    modifier = Modifier.width(60.dp),
                    color = Color(0xFF72AF7B),
                    trackColor = Color.LightGray
                )

            } else if (errorMessage != null) {
                Text(text = errorMessage!!, color = Color.Red)
            } else {

                if (isShowBorrowing){

                    if (historyItems.isNullOrEmpty()){

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Currently Borrowing:",
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(50.dp)
                        )

                        Text(
                            text = "No History Found",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                    } else {

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Currently Borrowing:",
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        LazyColumn {
                            items(historyItems!!) { item ->

                                if(item.status != "Returned"){

                                    Card(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxWidth()
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                        ){

                                            Text(
                                                text = "Title: ${item.title}",
                                                fontWeight = FontWeight(600),
                                                fontSize = 18.sp,
                                                color = Color.Black
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Text(
                                                text = "Book Code: ${item.bookCode}",
                                                fontWeight = FontWeight(400),
                                                fontSize = 13.sp,
                                                color = Color(0xFF5E6366)
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Text(
                                                text = "Borrowed on: ${item.borrowedDate}",
                                                fontWeight = FontWeight(500),
                                                fontSize = 16.sp,
                                                color = Color.Black
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Text(
                                                text = "Due Date: ${item.dueDate}",
                                                fontWeight = FontWeight(500),
                                                fontSize = 16.sp,
                                                color = Color.Black
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .background(Color.Red.copy(alpha = 0.4f), shape = RoundedCornerShape(10.dp))
                                                    .padding( horizontal = 5.dp, vertical = 5.dp)
                                            ){
                                                Text(
                                                    text = "Return book before Due Date",
                                                    fontWeight = FontWeight(700),
                                                    fontSize = 16.sp,
                                                    color = Color.White
                                                )
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }

                } else if (isShowBorrowed){

                    val returnedBooks = historyItems?.filter { it.status == "Returned" }

                    if (returnedBooks.isNullOrEmpty()) {

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Borrowed Books:",
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(50.dp)
                        )

                        Text(
                            text = "No History Found",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                    } else{
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Borrowed Books:",
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        LazyColumn {
                            items(historyItems!!) { item ->

                                if(item.status == "Returned"){

                                    Card(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxWidth()
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                        ){

                                            Text(
                                                text = "Title: ${item.title}",
                                                fontWeight = FontWeight(600),
                                                fontSize = 18.sp,
                                                color = Color.Black
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Text(
                                                text = "Book Code: ${item.bookCode}",
                                                fontWeight = FontWeight(400),
                                                fontSize = 13.sp,
                                                color = Color(0xFF5E6366)
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Text(
                                                text = "Borrowed on: ${item.borrowedDate}",
                                                fontWeight = FontWeight(500),
                                                fontSize = 16.sp,
                                                color = Color.Black
                                            )

                                            Spacer(
                                                modifier = Modifier.height(8.dp)
                                            )

                                            Box(
                                                modifier = Modifier
                                                    .background(Color.Green.copy(alpha = 0.4f), shape = RoundedCornerShape(10.dp))
                                                    .padding( horizontal = 5.dp, vertical = 5.dp)
                                            ){
                                                Text(
                                                    text = "Book ${item.status}",
                                                    fontWeight = FontWeight(700),
                                                    fontSize = 16.sp,
                                                    color = Color.White
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }


                } else if (isShowAttendance){

                    if (attendanceItems.isNullOrEmpty()){

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Attendance in Library:",
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(50.dp)
                        )

                        Text(
                            text = "No History Found",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                    } else {

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Attendance in Library:",
                            fontWeight = FontWeight(600),
                            fontSize = 20.sp,
                            color = Color.Black
                        )

                        Spacer(
                            modifier = Modifier.height(3.dp)
                        )

                        LazyColumn {
                            items(attendanceItems!!) { item ->

                                if (item.day == "Monday" || item.day == "Tuesday" || item.day == "Wednesday" || item.day == "Thursday" || item.day == "Friday" || item.day == "Saturday"){

                                    Card(
                                        modifier = Modifier
                                            .padding(10.dp)
                                            .fillMaxWidth()
                                    ) {

                                        Column(
                                            modifier = Modifier
                                                .padding(16.dp)
                                        ) {

                                            Text(
                                                text = "Date: ${item.entryTime}",
                                                fontWeight = FontWeight(600),
                                                fontSize = 18.sp,
                                                color = Color.Black
                                            )

                                            Spacer(
                                                modifier = Modifier.width(20.dp)
                                            )

                                            Text(
                                                text = item.day,
                                                fontWeight = FontWeight(600),
                                                fontSize = 18.sp,
                                                color = Color.DarkGray
                                            )
                                        }
                                    }
                                } else{

                                    Spacer(
                                        modifier = Modifier.height(30.dp)
                                    )
                                    Text(
                                        text = "No History Found",
                                        fontWeight = FontWeight(600),
                                        fontSize = 20.sp
                                    )
                                }
                            }
                        }
                    }


                } else{
                    Text(" Error ")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StaffTeamPage(navController: NavHostController){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Staff Team",
                        fontWeight = FontWeight(700)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(15.dp)
        ) {
            items(1) {

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "Easily connect with library staff for assistance, book inquiries, and general support. " +
                            "Reach out for help with borrowing, returns, or any library-related questions.",
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontWeight = FontWeight(400)
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "LIBRARY STAFF",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )

                Column (
                    modifier = Modifier
                        .padding(15.dp)

                ){

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = cardColors(
                            containerColor = Color(0xFF72AF7B)
                        ),
                        elevation = cardElevation(
                            defaultElevation = 10.dp
                        ),

                    ) {

                        Column {

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Spacer(
                                    modifier = Modifier.width(10.dp)
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.placeholder1),
                                    contentDescription = "Head",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                Column {

                                    Text(
                                        text = "Head Librarian",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(600)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .width(150.dp)
                                            .background(Color.White)
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Text(
                                        text = "Charles Ugip",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(700)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = cardColors(
                            containerColor = Color(0xFF72AF7B)
                        ),
                        elevation = cardElevation(
                            defaultElevation = 10.dp
                        ),

                    ) {

                        Column {

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Spacer(
                                    modifier = Modifier.width(10.dp)
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.placeholder1),
                                    contentDescription = "Head",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                Column {

                                    Text(
                                        text = "Head Librarian",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(600)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .width(150.dp)
                                            .background(Color.White)
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Text(
                                        text = "Charles Ugip",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(700)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "DEVELOPMENT TEAM",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )

                Column (
                    modifier = Modifier
                        .padding(15.dp)
                ){

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = cardColors(
                            containerColor = Color(0xFF72AF7B)
                        ),
                        elevation = cardElevation(
                            defaultElevation = 10.dp
                        ),

                        ) {

                        Column {

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Spacer(
                                    modifier = Modifier.width(10.dp)
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.charles),
                                    contentDescription = "Charles",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                Column {

                                    Text(
                                        text = "Project Manager",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(600)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .width(150.dp)
                                            .background(Color.White)
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Text(
                                        text = "Charles Toliao",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(700)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = cardColors(
                            containerColor = Color(0xFF72AF7B)
                        ),
                        elevation = cardElevation(
                            defaultElevation = 10.dp
                        ),

                        ) {

                        Column {

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Spacer(
                                    modifier = Modifier.width(10.dp)
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.gabriel),
                                    contentDescription = "gabriel",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                Column {

                                    Text(
                                        text = "Web Engineer",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(600)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .width(150.dp)
                                            .background(Color.White)
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Text(
                                        text = "Gabriel Esperanza",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(700)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = cardColors(
                            containerColor = Color(0xFF72AF7B)
                        ),
                        elevation = cardElevation(
                            defaultElevation = 10.dp
                        ),

                        ) {

                        Column {

                            Row (
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Spacer(
                                    modifier = Modifier.width(10.dp)
                                )

                                Image(
                                    painter = painterResource(id = R.drawable.nick),
                                    contentDescription = "nick",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                )

                                Spacer(
                                    modifier = Modifier.width(20.dp)
                                )

                                Column {

                                    Text(
                                        text = "Mobile Engineer",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(600)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .height(1.dp)
                                            .width(150.dp)
                                            .background(Color.White)
                                    )

                                    Spacer(
                                        modifier = Modifier.height(5.dp)
                                    )

                                    Text(
                                        text = "Joshua Velasco",
                                        color = Color.White,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight(700)
                                    )
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicesPage(navController: NavHostController){


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Services",
                        fontWeight = FontWeight(700)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(15.dp)
        ) {
            items(1){

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "CIRCULATION POLICY",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )

                ){
                    Column {

                        Text(
                            modifier = Modifier
                                .padding(13.dp),
                            text = "Borrowing Procedure",
                            fontSize = 18.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)
                        )

                        val bullet = "\u2022"
                        val borrowingProcedurePoints = listOf(
                            "The student should secure a Library Card in order to use the library facilities/borrow any library material.",
                            "Search OPAC >> Retrieve material >> Checkout materials at the circulation desks.",
                            "Reference, Filipiniana, periodical and non-book materials are for room use only but maybe borrowed and brought out of the library for photocopying purposes.",
                            "No books shall be loaned out for home use one week before the final examinations."
                        )
                        val paragraphStyle = ParagraphStyle(
                            textIndent = TextIndent(
                                restLine = 12.sp
                            )
                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp)
                                .offset(y = (-15).dp),

                            text = buildAnnotatedString {
                                borrowingProcedurePoints.forEach {
                                    withStyle(style = paragraphStyle) {
                                        append(bullet)
                                        append("\t\t") // Add a tab character to create an indentation / Can also be used in Tables
                                        append(it)
                                    }
                                }
                            },
                            fontSize = 16.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)

                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp),
                            text = "Return Procedure",
                            fontSize = 18.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)
                        )

                        val returnProcedurePoints = listOf(
                            "All circulating materials must be returned to where they were borrowed; books for home use should be borrowed not earlier than 4pm, " +
                                    "and must be returned not later than 9am on the due date."
                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp)
                                .offset(y = (-15).dp),

                            text = buildAnnotatedString {
                                returnProcedurePoints.forEach {
                                    withStyle(style = paragraphStyle) {
                                        append(bullet)
                                        append("\t\t") // Add a tab character to create an indentation / Can also be used in Tables
                                        append(it)
                                    }
                                }
                            },
                            fontSize = 16.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)

                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp),
                            text = "Renewal",
                            fontSize = 18.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)
                        )

                        val renewalPoints = listOf(
                            "Circulating materials if not in demand are subject for renewal on the due date if material is still needed.",
                            "Go to circulation desk for assistance."
                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp)
                                .offset(y = (-15).dp),

                            text = buildAnnotatedString {
                                renewalPoints.forEach {
                                    withStyle(style = paragraphStyle) {
                                        append(bullet)
                                        append("\t\t") // Add a tab character to create an indentation / Can also be used in Tables
                                        append(it)
                                    }
                                }
                            },
                            fontSize = 16.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)

                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp),
                            text = "Library Fines",
                            fontSize = 18.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)
                        )

                        val libraryFinesPoints = listOf(
                            "Overdue Circulation Books - P5.00 per hour.",
                            "Overdue Fiction Books - P5.00 per day.",
                            "Lost Library Card - P20.00.",
                            "All payments shall be made to the cashier"
                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp)
                                .offset(y = (-15).dp),

                            text = buildAnnotatedString {
                                libraryFinesPoints.forEach {
                                    withStyle(style = paragraphStyle) {
                                        append(bullet)
                                        append("\t\t") // Add a tab character to create an indentation / Can also be used in Tables
                                        append(it)
                                    }
                                }
                            },
                            fontSize = 16.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)

                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp),
                            text = "Lost Book",
                            fontSize = 18.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)
                        )

                        val lostBookPoints = listOf(
                            "Lost book shall replaced by same book (same title and author) or later edition, and the corresponding fine must be paid. If the book is irreplaceable, " +
                                    "200% of the current market price of the book must be paid by the borrower, and additional 100% to cover the acquisition and processing costs."
                        )

                        Text(
                            modifier = Modifier
                                .padding(13.dp)
                                .offset(y = (-15).dp),

                            text = buildAnnotatedString {
                                lostBookPoints.forEach {
                                    withStyle(style = paragraphStyle) {
                                        append(bullet)
                                        append("\t\t") // Add a tab character to create an indentation / Can also be used in Tables
                                        append(it)
                                    }
                                }
                            },
                            fontSize = 16.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)

                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Text(
                    text = "LIBRARY HOURS",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )

                ) {
                    Column(
                        modifier = Modifier
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row {

                            Column (
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){

                                Text(
                                    text = "Main Library",
                                    fontSize = 17.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(700)
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                Text(
                                    text = "Monday - Friday",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(500)
                                )

                                Text(
                                    text = "7:30 AM - 6:00 PM",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(400)
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                Text(
                                    text = "Saturday",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(500)
                                )
                                Text(
                                    text = "8:00 AM - 5:00 PM",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(400)
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(20.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(110.dp)
                                    .background(Color.Black)
                            )

                            Spacer(
                                modifier = Modifier.width(20.dp)
                            )

                            Column (
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){

                                Text(
                                    text = "Law Library",
                                    fontSize = 17.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(700)
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                Text(
                                    text = "Monday - Friday",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(500)
                                )

                                Text(
                                    text = "7:30 AM - 5:30 PM",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(400)
                                )

                                Spacer(
                                    modifier = Modifier.height(10.dp)
                                )

                                Text(
                                    text = "Saturday",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(500)
                                )

                                Text(
                                    text = "8:00 AM - 5:00 PM",
                                    fontSize = 15.sp,
                                    color = Color(0xFF5E6366),
                                    fontWeight = FontWeight(400)
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(20.dp)
                        )

                        Text(
                            text = "Holidays and Sunday:",
                            fontSize = 16.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(500)
                        )

                        Text(
                            text = "Libraries are Closed",
                            fontSize = 15.sp,
                            color = Color(0xFF5E6366),
                            fontWeight = FontWeight(400)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsPage(navController: NavHostController){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "About Us",
                        fontWeight = FontWeight(700)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .padding(15.dp)
        ) {
            items(1){

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "PHINMA UPANG LIBRARY",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )

                ){

                    Text(
                        modifier = Modifier
                        .padding(13.dp),
                        text = "The Library works to strengthen its facilities and services in consonance with the aim of the university which is to provide accessible, relevant, and appropriate training and education " +
                                "that leads to employment in their economic well-being. The library aims provide equal access and to library resources and services among students, faculty, " +
                                "and other members of the community through efficient provision of organized, relevant, up-to-date information sources, qualified personnel and properly maintained " +
                                "facilities to enable them to achieve excellence in carrying out their educational and instructional functions.",
                        fontSize = 16.sp,
                        color = Color(0xFF5E6366),
                        fontWeight = FontWeight(500)

                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "LIBRARY OBJECTIVES",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight(700)
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(20.dp)
                        )

                ){

                    val bullet = "\u2022"
                    val libraryObjectivePoints = listOf(
                        "To uphold the mission statement of the University and be an instrument in its ultimate realization.",
                        "To provide a collection that will develop among students constructive and critical thinking.",
                        "To make the library a place for continuous education.",
                        "To acquaint the students with the proper use of books and other library materials, and to encourage in them personal investigation and research.",
                        "To provide a collection that is extensively supportive of the curricular extension and research programs of the University."
                    )
                    val paragraphStyle = ParagraphStyle(
                        textIndent = TextIndent(
                            restLine = 12.sp
                        )
                    )
                    Text(
                        modifier = Modifier
                            .padding(13.dp),

                        text = buildAnnotatedString {
                            libraryObjectivePoints.forEach {
                                withStyle(style = paragraphStyle) {
                                    append(bullet)
                                    append("\t\t") // Add a tab character to create an indentation / Can also be used in Tables
                                    append(it)
                                }
                            }
                        },
                        fontSize = 16.sp,
                        color = Color(0xFF5E6366),
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }
    }
}
