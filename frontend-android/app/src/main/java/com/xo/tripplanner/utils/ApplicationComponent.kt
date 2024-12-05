package com.xo.tripplanner.utils

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.xo.tripplanner.R

data class Populardestination(
    @DrawableRes val image : Int,
    val name : String
)
 val PopularDestinations = mutableListOf(
     Populardestination(R.drawable.japan, "Japan"),
     Populardestination(R.drawable.london, "London"),
     Populardestination(R.drawable.new_york, "New York City"),
     Populardestination(R.drawable.paris, "Paris"),
     Populardestination(R.drawable.scotland, "Scotland"),
     Populardestination(R.drawable.sanfrancisco, "San Francisco"),
     Populardestination(R.drawable.tokyo, "Tokyo"),
 )

val gradientColor = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
