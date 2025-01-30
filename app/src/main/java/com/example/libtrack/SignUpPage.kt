package com.example.libtrack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(navHostController: NavHostController){

    // For text fields / Text State
    val firstNameTS = remember { mutableStateOf("") }
    val lastNameTS = remember { mutableStateOf("") }
    val schoolEmailTS = remember { mutableStateOf("") }
    val studentIdTS = remember { mutableStateOf("") }
    val contactNumbTS = remember { mutableStateOf("") }
    val passwordTS = remember { mutableStateOf("") }
    val confirmPasswordTS = remember { mutableStateOf("") }

    val programCourseList = listOf("BSIT","BSED","BSN")
    val yearLevelList = listOf("1st Year","2nd Year","3rd Year","4th Year")

    var selectedProgramCourse by remember { mutableStateOf(programCourseList[0]) }
    var selectedYearLevel by remember { mutableStateOf(yearLevelList[0]) }

    var isProgramCourseExpanded by remember { mutableStateOf(false) }
    var isYearLevelExpanded by remember { mutableStateOf(false) }


    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
    ) {

        ExposedDropdownMenuBox(
            expanded = isProgramCourseExpanded,
            onExpandedChange = { isProgramCourseExpanded = !isProgramCourseExpanded }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedProgramCourse,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isProgramCourseExpanded) },
            )

            ExposedDropdownMenu(
                expanded = isProgramCourseExpanded,
                onDismissRequest = { isProgramCourseExpanded = false }
            ) {
                programCourseList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedProgramCourse = item}
                    )
                }
            }

        }

        ExposedDropdownMenuBox(
                    expanded = isYearLevelExpanded,
                    onExpandedChange = { isYearLevelExpanded = !isYearLevelExpanded }
                ) {
                    TextField(
                        modifier = Modifier.menuAnchor(),
                        value = selectedYearLevel,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isYearLevelExpanded) },
                    )

                    ExposedDropdownMenu(
                        expanded = isYearLevelExpanded,
                        onDismissRequest = { isYearLevelExpanded = false }
                    ) {
                        yearLevelList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedYearLevel = item}
                            )
                        }
                    }

                }



        // First Name
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = firstNameTS.value,
            onValueChange = { firstNameTS.value = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "First Name",
                    fontWeight = FontWeight(600)
                )
            },
            singleLine = true
        )

        // Last Name
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = lastNameTS.value,
            onValueChange = { lastNameTS.value = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Last Name",
                    fontWeight = FontWeight(600)
                )
            },
            singleLine = true
        )

        // School Email
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = schoolEmailTS.value,
            onValueChange = { schoolEmailTS.value = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "School Email",
                    fontWeight = FontWeight(600)
                )
            },
            singleLine = true
        )

        // Student ID
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = studentIdTS.value,
            onValueChange = { studentIdTS.value = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Student ID",
                    fontWeight = FontWeight(600)
                )
            },
            singleLine = true
        )

        // Contact Number
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = contactNumbTS.value,
            onValueChange = { contactNumbTS.value = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Contact Number",
                    fontWeight = FontWeight(600)
                )
            },
            singleLine = true
        )

        // Password
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = passwordTS.value,
            onValueChange = { passwordTS.value = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Password",
                    fontWeight = FontWeight(600)
                )
            },
            singleLine = true
        )

        // Confirm Password
        TextField(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                )
                .background(
                    color = Color(0xFFC1C1C1),
                    shape = RoundedCornerShape(15.dp)
                ),
            value = confirmPasswordTS.value,
            onValueChange = { confirmPasswordTS.value = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Confirm Password",
                    fontWeight = FontWeight(600)
                )
            },
            singleLine = true
        )

        var isChecked by remember { mutableStateOf(false) }

        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = it}
        )

        Button(
            onClick = {
                // all of the variable TS will be inserted to the DB
            },
            modifier = Modifier
                .size(width = 100.dp, height = 40.dp)
        ) {
            Text("Ok po")
        }




    }
}