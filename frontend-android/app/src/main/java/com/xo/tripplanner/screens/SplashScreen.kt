package com.xo.tripplanner.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xo.tripplanner.R
import com.xo.tripplanner.Screen
import com.xo.tripplanner.TripViewModel
import com.xo.tripplanner.ui.theme.afacadFlux
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavController, viewModel: TripViewModel) {
    val imageOffsetY = remember { Animatable(0f) }
    val textOffsetY = remember { Animatable(0f) }
    val text = remember {mutableStateOf("")}
    val tokenState = viewModel.token.collectAsState(initial = null)

    LaunchedEffect(Unit) {
        delay(500)
        launch {
            imageOffsetY.animateTo(-130f, animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing))
        }
        launch { text.value = "Welcome to Trip Planner"
          textOffsetY.animateTo(150f, animationSpec = tween(durationMillis = 500, easing = FastOutLinearInEasing))
        }
        delay(2000L)
        if (!tokenState.value.isNullOrEmpty()) {
            navController.navigate(Screen.Home.route) {
                popUpTo(0)
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(0)
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text.value, fontSize = 24.sp, fontFamily = afacadFlux, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.offset { IntOffset(0, textOffsetY.value.toInt()) }.padding(top = 20.dp))
            Image(
                painter = painterResource(id = R.drawable.calendar),
                contentDescription = "Logo",
                modifier = Modifier.offset { IntOffset(0, imageOffsetY.value.toInt()) })
        }
    }
}
