package com.xo.tripplanner.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Details
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xo.tripplanner.TripViewModel
import com.xo.tripplanner.ui.theme.afacadFlux
import com.xo.tripplanner.utils.bottomNavBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTrip(navController: NavController, viewModel: TripViewModel) {
    val place = remember { mutableStateOf("") }
    val details = remember { mutableStateOf("") }
    var showFromDatePicker by remember { mutableStateOf(false) }
    var showToDatePicker by remember { mutableStateOf(false) }
    val fromDatePickerState = rememberDatePickerState()
    val toDatePickerState = rememberDatePickerState()

    val selectedFromDate = fromDatePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val selectedToDate = toDatePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        bottomBar = {
            bottomNavBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = "Plan a new trip",
                fontSize = 30.sp,
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                color = colorScheme.onSurface
            )
            Text(
                "Create a trip and invite your friends \nto collaborate and enjoy together.",
                fontSize = 15.sp,
                color = colorScheme.onSurface,
                fontFamily = afacadFlux,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            androidx.compose.material.OutlinedTextField(
                value = place.value,
                onValueChange = { place.value = it },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.LocationOn,
                        contentDescription = "Email",
                        modifier = Modifier.size(20.dp),
                        tint = colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = "Enter the Location",
                        style = typography.bodySmall,
                        color = colorScheme.onSurface
                    )
                },
                textStyle = TextStyle(fontSize = 10.sp),
                modifier = Modifier
                    .width(240.dp)
                    .height(60.dp),
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorScheme.primary,
                    unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
                    textColor = colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = details.value,
                onValueChange = { details.value = it },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Details,
                        contentDescription = "Email",
                        modifier = Modifier.size(20.dp),
                        tint = colorScheme.onSurface
                    )
                },
                label = {
                    Text(
                        text = "Enter the Details",
                        style = typography.bodySmall,
                        color = colorScheme.onSurface
                    )
                },
                textStyle = TextStyle(fontSize = 10.sp),
                modifier = Modifier
                    .width(240.dp)
                    .height(80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorScheme.primary,
                    unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
                    textColor = colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                OutlinedTextField(
                    value = selectedFromDate,
                    onValueChange = { },
                    label = {
                        Text(
                            "Date from",
                            style = typography.bodySmall,
                            color = colorScheme.onSurface
                        )
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showFromDatePicker = !showFromDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(56.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(22.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
                        textColor = colorScheme.onSurface
                    )
                )
                OutlinedTextField(
                    value = selectedToDate,
                    onValueChange = { },
                    label = {
                        Text(
                            "Date to",
                            style = typography.bodySmall,
                            color = colorScheme.onSurface
                        )
                    },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showToDatePicker = !showToDatePicker }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(56.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(22.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.5f),
                        textColor = colorScheme.onSurface
                    )
                )

                if (showFromDatePicker) {
                    AlertDialog(
                        onDismissRequest = { showFromDatePicker = false },
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 4.dp)
                                .background(colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = fromDatePickerState,
                                showModeToggle = false,
                            )
                        }
                    }
                }

                if (showToDatePicker) {
                    AlertDialog(
                        onDismissRequest = { showToDatePicker = false },
                        modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(elevation = 4.dp)
                                .background(colorScheme.surface)
                                .padding(16.dp)
                        ) {
                            DatePicker(
                                state = toDatePickerState,
                                showModeToggle = false,
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                "Now create the trip?",
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                if (place.value.isEmpty() || details.value.isEmpty() || selectedToDate.isEmpty() || selectedToDate.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Please enter all the details carefully",
                        Toast.LENGTH_SHORT
                    ).show()
                }else{
                    viewModel.createTrip(
                        title = place.value,
                        description = details.value,
                        startDate = selectedFromDate,
                        endDate = selectedToDate,
                        context = context
                    )
                }
            }, shape = RoundedCornerShape(20.dp)) {
                Text("Create Trip", fontFamily = afacadFlux, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                "**By creating the trip you will be able to see \nyour created trips and also \nyou can add the collaborators as well.**",
                fontFamily = afacadFlux, textAlign = TextAlign.Center
            )
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}