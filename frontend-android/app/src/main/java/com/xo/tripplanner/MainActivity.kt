package com.xo.tripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xo.tripplanner.screens.CreateTrip
import com.xo.tripplanner.screens.DetailsScreen
import com.xo.tripplanner.screens.HomeScreen
import com.xo.tripplanner.screens.LoginScreen
import com.xo.tripplanner.screens.ProfileScreen
import com.xo.tripplanner.screens.RegisterScreen
import com.xo.tripplanner.screens.SplashScreen
import com.xo.tripplanner.ui.theme.TripPlannerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TripPlannerTheme {
                NavigationScreen()
            }
        }
    }
}

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Details : Screen("details/{tripId}") {
        fun createRoute(tripId: String): String = "details/$tripId"
    }
    object CreateTrip : Screen("create_trip")
    object Profile : Screen("profile")
}

@Composable
fun NavigationScreen(){
    val viewModel : TripViewModel = viewModel()
    val navController = rememberNavController();
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController, viewModel)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController, viewModel)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController, viewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController, viewModel)
        }
        composable(Screen.Details.route) {
            val tripId = it.arguments?.getString("tripId") ?: ""
            DetailsScreen(tripId,navController, viewModel)
        }
        composable(Screen.CreateTrip.route) {
            CreateTrip(navController, viewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController, viewModel)
        }
    }
}