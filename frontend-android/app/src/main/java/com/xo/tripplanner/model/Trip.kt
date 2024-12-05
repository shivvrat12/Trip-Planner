package com.xo.tripplanner.model

data class Trip(
    val _id: String,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val creator: String,
    val collaborators: List<String>
)
