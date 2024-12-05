package com.xo.tripplanner.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xo.tripplanner.TripViewModel
import com.xo.tripplanner.ui.theme.afacadFlux

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(id: String, navController: NavController, viewModel: TripViewModel) {

    val context = LocalContext.current
    val tripDetails = viewModel.findTrip(id)
    val collaboratorDetails = viewModel.collaborators.collectAsState()
    val isInviteEnabled = remember { mutableStateOf(false) }
    val email = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getCollaboratorAndCreatorNames(tripDetails.creator, tripDetails.collaborators)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding()) {
        Icon(
            Icons.Outlined.ArrowBackIosNew,
            contentDescription = "Back",
            modifier = Modifier
                .systemBarsPadding()
                .padding(20.dp)
                .clickable { navController.popBackStack() }
        )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Text(
                text = tripDetails.title,
                modifier = Modifier.padding(10.dp),
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
            Divider()
            Text(
                text = tripDetails.description,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                Text(
                    text = "From: ${tripDetails.startDate.split("T")[0]}",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontFamily = afacadFlux,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "To: ${tripDetails.endDate.split("T")[0]}",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontFamily = afacadFlux,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Divider()
            Text(
                text = "Created By: ${collaboratorDetails.value.creator}",
                modifier = Modifier.padding(horizontal = 10.dp),
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Text(
                text = "Collaborators: ${collaboratorDetails.value.collaborators}",
                modifier = Modifier.padding(horizontal = 10.dp),
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { isInviteEnabled.value = !isInviteEnabled.value },
                shape = RoundedCornerShape(20.dp)) {
                Text("Add Collaborators", fontFamily = afacadFlux, fontWeight = FontWeight.SemiBold)
            }
            if (isInviteEnabled.value) {
                AlertDialog(onDismissRequest = { isInviteEnabled.value = false }){
                    Column(modifier = Modifier.clip(RoundedCornerShape(20.dp)).background(Color.White).padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Invite Collaborators",)
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedTextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Email,
                                    contentDescription = "Email",
                                    modifier = Modifier.size(20.dp),
                                    tint = colorScheme.onSurface
                                )
                            },
                            label = {
                                Text(
                                    text = "Email Address",
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
                        Button(onClick = {
                            viewModel.addCollaborator(tripDetails._id, email.value, context)
                            isInviteEnabled.value = false
                            viewModel.getCollaboratorAndCreatorNames(tripDetails.creator, tripDetails.collaborators)
                        },shape = RoundedCornerShape(20.dp)) {
                            Text("Add", fontFamily = afacadFlux, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("**Make sure the email address you will add is already registered on the Application**", fontFamily = afacadFlux)
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}