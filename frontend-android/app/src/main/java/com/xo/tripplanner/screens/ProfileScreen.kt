package com.xo.tripplanner.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xo.tripplanner.Screen
import com.xo.tripplanner.TripViewModel
import com.xo.tripplanner.ui.theme.afacadFlux
import com.xo.tripplanner.utils.bottomNavBar

@Composable
fun ProfileScreen(navController: NavController, viewModel: TripViewModel) {
    val userData = viewModel.userData.collectAsState()
    val trips = viewModel.trips.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getUserData()
    }
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
            Text(
                "Profile",
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(colorScheme.primary.copy(alpha = 0.3f)),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(
                    userData.value?.name ?: "No Name Available for now",
                    fontFamily = afacadFlux,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    "Email: ${userData.value?.email ?: "No Email Available for now"}",
                    fontFamily = afacadFlux,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Note: This is not the final build of this project so as of now you can't able to edit the profile details maybe in future i will add it.",
                    fontFamily = afacadFlux,
                    modifier = Modifier.padding(20.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    textDecoration = TextDecoration.Underline
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Your trips",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = afacadFlux,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Divider()
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)) {
                    if (trips.value.isNotEmpty()) {
                        LazyColumn() {
                            items(trips.value) { item ->
                                tripView(onClick = {
                                    navController.navigate(Screen.Details.createRoute(item._id))
                                },
                                    title = item.title,
                                    desc = item.description,
                                    from = item.startDate,
                                    to = item.endDate)
                            }
                        }
                    }
                    else{
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(text = "No trips found", fontFamily = afacadFlux, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun tripView(onClick: () -> Unit, title: String, desc: String, from: String, to: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.Blue.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
    ) {
            Text(
                text = title,
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = desc,
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.White,
            fontFamily = afacadFlux,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "From: ${from.split("T")[0]}",
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.White,
            fontFamily = afacadFlux,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
            Text(
            text = "To: ${to.split("T")[0]}",
            modifier = Modifier.padding(horizontal = 10.dp),
            color = Color.White,
            fontFamily = afacadFlux,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

    }
}