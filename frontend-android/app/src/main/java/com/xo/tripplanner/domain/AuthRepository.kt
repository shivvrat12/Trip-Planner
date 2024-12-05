package com.xo.tripplanner.domain

import com.xo.tripplanner.data.remote.TripPlannerApi
import com.xo.tripplanner.model.AuthResponse
import com.xo.tripplanner.model.RegisterRequest
import com.xo.tripplanner.model.LoginRequest
import com.xo.tripplanner.model.UserData
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val api: TripPlannerApi
) {
    suspend fun registerUser(registerRequest: RegisterRequest): AuthResponse {
        return api.registerUser(registerRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest): AuthResponse {
        return api.loginUser(loginRequest)
    }

    suspend fun getUserData(token: String): UserData {
        return api.getUserProfile("Bearer $token")
    }
}