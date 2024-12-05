package com.xo.tripplanner.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xo.tripplanner.R
import com.xo.tripplanner.Screen
import com.xo.tripplanner.TripViewModel
import com.xo.tripplanner.ui.theme.afacadFlux
import com.xo.tripplanner.utils.Populardestination
import com.xo.tripplanner.utils.bottomNavBar
import com.xo.tripplanner.utils.gradientColor


@Composable
fun HomeScreen(navController: NavController, viewModel: TripViewModel) {
    val trips = viewModel.trips.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTrips()
    }

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .systemBarsPadding(),
        bottomBar = {
            bottomNavBar(navController)
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "Logo",
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "Trip Planner",
                    fontFamily = afacadFlux,
                    fontWeight = FontWeight.Normal,
                    fontSize = 26.sp,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pexels_ajaybhargavguduru_939715),
                    contentDescription = "Trip",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()

                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        "Get inspired \nfor your trip \nto Manali",
                        fontFamily = afacadFlux,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 28.sp
                    )
                    Button(onClick = {}, shape = RoundedCornerShape(22.dp)) {
                        Text("Explore Now", fontFamily = afacadFlux)
                    }
                }
            }
            Text(
                text = "Popular Destinations",
                fontFamily = afacadFlux,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )
            LazyRow(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                items(com.xo.tripplanner.utils.PopularDestinations) { popular ->
                    PopularDestination(popular)
                }
            }
        }
    }
}

@Composable
fun PopularDestination(popular: Populardestination) {
    Box(modifier = Modifier
        .height(200.dp)
        .width(140.dp).padding(horizontal = 6.dp).clip(RoundedCornerShape(20.dp))) {
        Image(
            painter = painterResource(id = popular.image),
            contentDescription = "Popular Destination",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(modifier = Modifier.fillMaxSize().background(brush = Brush.verticalGradient(gradientColor)))
        Text(text = popular.name, fontFamily = afacadFlux, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color.White, modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp))
    }
}