package com.xo.tripplanner.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xo.tripplanner.Screen


data class bottomNavBarObjects(
    val icon: ImageVector,
    val text: String,
    val route: String,
)

val bottomNavigationItems = mutableListOf(
    bottomNavBarObjects(Icons.Outlined.Home, "Home", Screen.Home.route),
    bottomNavBarObjects(Icons.Outlined.Add, "Create Trip", Screen.CreateTrip.route),
    bottomNavBarObjects(Icons.Outlined.PersonOutline, "Profile", Screen.Profile.route),
)

@Composable
fun bottomNavBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    Row(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))) {
        bottomNavigationItems.forEach {
            val isSelected = it.route == currentRoute
            val tintColor =
                if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface.copy(
                    alpha = 0.5f
                )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clickable {
                        if (it.route != currentRoute) {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                    .background(tintColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = it.icon, contentDescription = it.text, tint = Color.White)
                Text(text = it.text, color = Color.White)
            }
        }
    }
}
