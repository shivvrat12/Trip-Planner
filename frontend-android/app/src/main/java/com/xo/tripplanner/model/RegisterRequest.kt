package com.xo.tripplanner.model

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)
