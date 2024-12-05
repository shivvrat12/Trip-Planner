package com.xo.tripplanner.data.remote

import com.xo.tripplanner.model.AuthResponse
import com.xo.tripplanner.model.CollaboratorRequest
import com.xo.tripplanner.model.CollaboratorResponse
import com.xo.tripplanner.model.LoginRequest
import com.xo.tripplanner.model.RegisterRequest
import com.xo.tripplanner.model.Trip
import com.xo.tripplanner.model.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TripPlannerApi {
    @POST("api/users/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): AuthResponse

    @POST("api/users/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): AuthResponse

    @GET("api/trips")
    suspend fun getTrips(
        @Header("Authorization") token: String
    ): List<Trip>

    @PUT("api/trips/{id}/invite")
    suspend fun inviteToTrip(
        @Path("id") tripId: String,
        @Header("Authorization") token: String,
        @Body email: Map<String, String>
    ): Response<Unit>

    @POST("api/trips")
    suspend fun createTrip(
        @Header("Authorization") token: String,
        @Body trip: Map<String, String>
    ): Response<Trip>

    @GET("api/users/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserData

    @POST("api/users/collaborator-names")
    suspend fun getCollaboratorAndCreatorNames(
        @Header("Authorization") token: String,
        @Body data: CollaboratorRequest
    ): Response<CollaboratorResponse>
}